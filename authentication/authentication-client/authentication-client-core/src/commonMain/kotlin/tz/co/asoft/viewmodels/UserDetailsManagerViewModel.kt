@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.UserDetailsManagerViewModel.Intent
import tz.co.asoft.UserDetailsManagerViewModel.State

class UserDetailsManagerViewModel(
    private val repo: IUsersRepo
) : VModel<Intent, State>(State.Loading("Loading")) {
    sealed class State {
        class Loading(val msg: String) : State()
        class ShowDetails(val u: User, val viewer: User?) : State()
        class ShowPasswordForm(val user: User) : State()
        class ShowBasicInfoForm(val user: User) : State()
        class Error(val msg: String) : State()
    }

    sealed class Intent {
        class ViewPasswordForm(val user: User) : Intent()
        class ViewBasicInfoForm(val u: User) : Intent()
        class ViewUser(val user: User) : Intent()
        class ChangePassword(val old: String, val new: String) : Intent()
        class EditBasicInfo(val name: String, val email: String, val phone: String) : Intent()
        object SignOut : Intent()
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.ViewPasswordForm -> ui.value = State.ShowPasswordForm(i.user)
        is Intent.ViewBasicInfoForm -> ui.value = State.ShowBasicInfoForm(i.u)
        is Intent.ViewUser -> viewUser(i)
        is Intent.ChangePassword -> changePassword(i)
        is Intent.EditBasicInfo -> editBasicInfo(i)
        is Intent.SignOut -> launch { repo.signOut() }
    }

    val user get() = Authentication.state.value.user

    private fun CoroutineScope.editBasicInfo(i: Intent.EditBasicInfo) = launch {
        flow {
            emit(State.Loading("Editing information"))
            val u = user ?: throw Exception("No logged in user")
            emit(State.ShowDetails(repo.editBasicInfoAndReauthenticateIfNeedBe(u, i.name, Email(i.email), Phone(i.phone)).await(), u))
        }.catch {
            emit(State.Error("Failed to edit info: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.changePassword(i: Intent.ChangePassword) = launch {
        flow {
            emit(State.Loading("Changing your password"))
            val uid = user?.uid ?: throw Exception("No logged in user")
            emit(State.ShowDetails(repo.changePasswordThenStoreToken(uid, i.old, i.new).await(), user))
        }.catch {
            emit(State.Error("Failed to change password"))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.viewUser(i: Intent.ViewUser) = launch {
        flow {
            val u = user ?: throw Exception("No logged in user")
            emit(State.Loading("Loading User"))
            emit(State.ShowDetails(u = i.user, viewer = u))
        }.catch {
            emit(State.Error("Failed to display user"))
        }.collect { ui.value = it }
    }
}