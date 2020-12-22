@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tz.co.asoft.UsersManagerViewModel.Intent
import tz.co.asoft.UsersManagerViewModel.State

class UsersManagerViewModel(
    private val usersRepo: IUsersRepo,
    private val rolesRepo: IRepo<UserRole>
) : VModel<Intent, State>(State.Loading("Loading Form")) {
    companion object : IntentBus<Intent>() {
        var USERS_PER_PAGE = 10
    }

    private val pagingSource = GenericPagingSource(usersRepo)

    init {
        launch { collect { post(it) } }
        launch { post(Intent.ViewUsers(USERS_PER_PAGE, null)) }
    }

    sealed class State {
        data class Loading(val msg: String) : State()
        data class Form(
            val roles: List<UserRole>,
            val onCancel: () -> Unit = { post(Intent.ViewUsers(predicate = null)) },
            val onSubmit: (String, String, String, UserRole) -> Unit = { name, email, phone, role ->
                post(Intent.CreateUser(name, email, phone, role))
            }
        ) : State()

        data class Users(val pager: Pager<User>) : State()
        data class Error(val msg: String) : State()
        object Success : State()
    }

    sealed class Intent {
        data class ViewUsers(val usersPerPage: Int = USERS_PER_PAGE, val predicate: ((User) -> Boolean)?) : Intent()
        object ViewForm : Intent()
        data class CreateUser(val name: String, val email: String, val phone: String, val role: UserRole) : Intent()
    }

    override fun execute(i: Intent): Any = when (i) {
        is Intent.CreateUser -> createUser(i)
        is Intent.ViewForm -> loadForm()
        is Intent.ViewUsers -> ui.value = State.Users(pagingSource.pager(i.usersPerPage))
    }

    private fun loadForm() = launch {
        flow {
            emit(State.Loading("Loading form please wait"))
            emit(State.Form(rolesRepo.all()))
        }.catch {
            emit(State.Error("Failed to load form: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }

    private fun createUser(i: Intent.CreateUser) = launch {
        flow {
            emit(State.Loading("Creating user"))
            val claim = Claim(
                permits = listOf()
            )

            val (_, user) = usersRepo.register(
                claim = claim,
                accountName = i.name,
                userFullname = i.name,
                email = Email(i.email),
                phone = Phone(i.phone),
                password = "123456"
            )
            emit(State.Success)
        }.catch {
            emit(State.Error("Failed to create user"))
        }.collect {
            ui.value = it
        }
        delay(3000)
        post(Intent.ViewUsers(predicate = null))
    }
}