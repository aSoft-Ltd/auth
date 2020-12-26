@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tz.co.asoft.LoginFormViewModel.State

class LoginFormViewModel(
    private val repo: IUsersRepo
) : VModel<LoginFormViewModel.Intent, State>(State.ShowForm()) {

    sealed class State {
        data class Loading(val msg: String) : State()
        data class ShowForm(val email: String? = null) : State()
        data class AccountSelection(val user: User) : State()
        data class Error(val msg: String, val reason: String?, val stacktrace: String) : State()
        object Success : State()
    }

    sealed class Intent {
        data class SignIn(val email: String, val pwd: ByteArray) : Intent()
        data class AuthenticateAccount(val account: UserAccount, val user: User) : Intent()
    }

    override fun execute(i: Intent): Any = when (i) {
        is Intent.SignIn -> signIn(i)
        is Intent.AuthenticateAccount -> authenticateAccount(i)
    }

    private fun authenticateAccount(i: Intent.AuthenticateAccount) = launch {
        flow {
            emit(State.Loading("Authenticating ${i.account.name}"))
            repo.authenticateThenStoreToken(
                accountId = i.account.uid ?: throw Exception("Account Id can't be null"),
                userId = i.user.uid ?: throw Exception("User id can't be null")
            )
            emit(State.Success)
        }.terminateGracefully(i.user.emails.first())
    }

    private fun signIn(i: Intent.SignIn) = launch {
        flow {
            emit(State.Loading("Signing you in"))
            val res = repo.signInAndStoreToken(i.email, SHA256.digest(i.pwd).hex)
            val user = res.leftOrNull()
            if (user != null) {
                emit(State.AccountSelection(user))
            } else {
                emit(State.Success)
            }
        }.terminateGracefully(i.email)
    }

    private suspend fun Flow<State>.terminateGracefully(email: String) = catch {
        repeat(5) { seconds ->
            emit(State.Error("Failed to sign you in (${5 - seconds})", it.message, it.stackTraceToString()))
            delay(1000)
        }
        emit(State.ShowForm(email))
    }.collect {
        ui.value = it
    }
}
