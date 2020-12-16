@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.UserProfileContainerViewModel.Intent
import tz.co.asoft.UserProfileContainerViewModel.State

class UserProfileContainerViewModel(
    private val repo: IUsersRepo
) : VModel<Intent, State>(State.Loading("Loading")) {
    sealed class State {
        class Loading(val msg: String) : State()
        class Profile(val user: User) : State()
        class Error(val msg: String) : State()
    }

    sealed class Intent {
        class ViewProfile(val uid: String) : Intent()
    }

    override fun execute(i: Intent) = when (i) {
        is Intent.ViewProfile -> viewProfile(i)
    }

    private fun viewProfile(i: Intent.ViewProfile) = launch {
        flow {
            emit(State.Loading("Preparing profile"))
            val liveUser = Authentication.state.value.user
            val user = if (liveUser?.uid == i.uid) {
                liveUser
            } else {
                repo.load(i.uid)
            }

            if (user == null) {
                throw Exception("Failed to load user with uid: ${i.uid}")
            } else {
                emit(State.Profile(user))
            }
        }.catch {
            emit(State.Error(it.message ?: "Unknown error while loading user with uid: ${i.uid}"))
        }.collect {
            ui.value = it
        }
    }
}