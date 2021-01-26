@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tz.co.asoft.UsersManagerViewModel.Intent
import tz.co.asoft.UsersManagerViewModel.State

class UsersManagerViewModel(
    private val usersRepo: IUsersRepo,
    private val principle: IUserPrinciple,
    private val accounts: List<UserAccount.Type>
) : VModel<Intent, State>(State.Loading("Loading Form")) {
    companion object : IntentBus<Intent>() {
        var USERS_PER_PAGE = 10
    }

    private val pagingSource = GenericPagingSource(usersRepo)
    private var pager = pagingSource.pager(USERS_PER_PAGE)

    init {
        observeIntentBus()
        post(Intent.ViewUsers(USERS_PER_PAGE, null))
    }

    sealed class State {
        data class Loading(val msg: String) : State()
        data class Form(val accountTypes: List<UserAccount.Type>) : State() {
            val onCancel = { post(Intent.ViewUsers(predicate = null)) }
            val onSubmit = { name: String, email: String, phone: String, type: UserAccount.Type ->
                post(Intent.CreateUser(name, email, phone, type))
            }
        }

        data class Users(val pager: Pager<User>) : State()
        data class Error(val exception: Throwable, val origin: Intent) : State() {
            val cancel = { post(Intent.ViewUsers(predicate = null)) }
            val retry = { post(origin) }
        }

        class Success(val msg: String = "Success") : State()
    }

    sealed class Intent {
        data class ViewUsers(val usersPerPage: Int = USERS_PER_PAGE, val predicate: ((User) -> Boolean)?) : Intent()
        object ViewForm : Intent()
        data class CreateUser(val name: String, val email: String, val phone: String, val type: UserAccount.Type) : Intent()
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.CreateUser -> createUser(i)
        is Intent.ViewForm -> viewForm(i)
        is Intent.ViewUsers -> viewUsers(i)
    }

    private fun CoroutineScope.viewUsers(intent: Intent.ViewUsers) = launch {
        flow<State> {
            require(principle.has(User.Permissions.Read)) { "You are not authorized to view all users" }
            emit(State.Users(pager))
        }.catch {
            emit(State.Error(Exception("Failed to display all users for you", it), intent))
        }.collect { ui.value = it }
    }

    private fun CoroutineScope.viewForm(intent: Intent.ViewForm) = launch {
        flow {
            require(principle.has(User.Permissions.Create)) { "You are not authorized to create new users" }
            emit(State.Loading("Loading form please wait"))
            emit(State.Form(accounts))
        }.catch {
            emit(State.Error(Exception("Failed to load user form", it), intent))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.createUser(intent: Intent.CreateUser) = launch {
        flow {
            require(principle.has(User.Permissions.Create)) { "You are not authorized to create a new user" }
            emit(State.Loading("Creating user"))
            val (_, user) = usersRepo.register(
                accountType = intent.type,
                accountName = intent.name,
                userFullname = intent.name,
                email = Email(intent.email),
                phone = Phone(intent.phone),
                password = "123456".toByteArray()
            ).await()
            emit(State.Success())
        }.catch {
            emit(State.Error(Exception("Failed to create user", it), intent))
        }.collect {
            ui.value = it
        }
    }
}