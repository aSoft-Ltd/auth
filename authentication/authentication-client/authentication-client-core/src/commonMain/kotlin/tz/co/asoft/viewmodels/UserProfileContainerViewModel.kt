@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.UserProfileContainerViewModel.Intent
import tz.co.asoft.UserProfileContainerViewModel.State

class UserProfileContainerViewModel(
    private val repo: IUsersRepo,
    private val state: MutableStateFlow<SessionState>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()
    sealed class State {
        data class Loading(val msg: String) : State()
        data class Profile(val user: User) : State()
        data class Error(val exception: Throwable, val origin: Intent) : State() {
            val onRetry = { post(origin) }
        }
    }

    sealed class Intent {
        class ViewProfile(val uid: String) : Intent()
    }

    override fun CoroutineScope.execute(i: Intent) = when (i) {
        is Intent.ViewProfile -> viewProfile(i)
    }

    private fun CoroutineScope.viewProfile(i: Intent.ViewProfile) = launch {
        flow {
            emit(State.Loading("Preparing profile"))
            val liveUser = state.value.user
            val user = if (liveUser?.uid == i.uid) {
                liveUser
            } else {
                repo.load(i.uid).await()
            }

            if (user == null) {
                throw Exception("Failed to load user with uid: ${i.uid}")
            } else {
                emit(State.Profile(user))
            }
        }.catch {
            emit(State.Error(Exception("Failed to view profile", it), i))
        }.collect {
            ui.value = it
        }
    }
}