@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import tz.co.asoft.LoginFormViewModel.State

class LoginFormViewModel(
    private val repo: IUsersFrontendService
) : VModel<LoginFormViewModel.Intent, State>(State.ShowForm) {

    sealed class State {
        class Loading(val msg: String) : State()
        object ShowForm : State()
        class Error(val msg: String) : State()
        object Success : State()
    }

    sealed class Intent {
        object ShowForm : Intent()
        class SignIn(val email: String, val pwd: String) : Intent()
    }

    override fun execute(i: Intent): Any = when (i) {
        is Intent.ShowForm -> ui.value = State.ShowForm
        is Intent.SignIn -> signIn(i)
    }

    private fun signIn(i: Intent.SignIn) = launch {
        flow {
            emit(State.Loading("Signing you in"))
            repo.signInAndStoreToken(i.email, i.pwd)
            emit(State.Success)
        }.catch {
            emit(State.Error(it.cause?.message ?: "Unknown error"))
        }.toList()
        ui.value = State.Loading("Signing you in")
        if (ui.value is State.Error) {
            delay(3000)
            post(Intent.ShowForm)
        }
    }
}
