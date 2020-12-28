@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tz.co.asoft.LoginFormViewModel.State

class LoginFormViewModel(
    private val repo: IUsersRepo
) : VModel<LoginFormViewModel.Intent, State>(State.ShowForm(email = null)) {
    companion object : IntentBus<Intent>()
    sealed class State {
        data class Loading(val msg: String) : State()
        data class ShowForm(val email: String?) : State()
        data class AccountSelection(val user: User) : State()
        data class Error(val msg: String, val reason: String?, val stacktrace: String, val i: Intent) : State() {
            val onTryAgain = { post(i) }
            val onGoBack = { post(Intent.ViewForm(i.email)) }
        }

        object Success : State()
    }

    sealed class Intent(open val email: String?) {
        data class ViewForm(override val email: String?) : Intent(email)
        data class SignIn(override val email: String, val pwd: ByteArray) : Intent(email)
        data class AuthenticateAccount(val account: UserAccount, val user: User) : Intent(user.emails.firstOrNull())
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.SignIn -> signIn(i)
        is Intent.AuthenticateAccount -> authenticateAccount(i)
        is Intent.ViewForm -> ui.value = State.ShowForm(i.email)
    }

    private fun CoroutineScope.authenticateAccount(i: Intent.AuthenticateAccount) = launch {
        flow {
            emit(State.Loading("Authenticating ${i.account.name}"))
            repo.authenticateThenStoreToken(
                accountId = i.account.uid ?: throw Exception("Account Id can't be null"),
                userId = i.user.uid ?: throw Exception("User id can't be null")
            )
            emit(State.Success)
        }.catch {
            emit(State.Error("Failed to authenticate your accounts", it.message, it.stackTraceToString(), i))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.signIn(i: Intent.SignIn) = launch {
        flow {
            emit(State.Loading("Signing you in"))
            val res = repo.signInAndStoreToken(i.email, SHA256.digest(i.pwd).hex)
            val user = res.leftOrNull()
            if (user != null) {
                emit(State.AccountSelection(user))
            } else {
                emit(State.Success)
            }
        }.catch {
            emit(State.Error("Failed to sign you in", it.message, it.stackTraceToString(), i))
        }.collect {
            ui.value = it
        }
    }
}
