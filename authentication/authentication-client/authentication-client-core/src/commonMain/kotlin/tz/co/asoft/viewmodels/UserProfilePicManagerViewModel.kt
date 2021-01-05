@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tz.co.asoft.UserProfilePicManagerViewModel.Intent
import tz.co.asoft.UserProfilePicManagerViewModel.State

class UserProfilePicManagerViewModel(
    private val repo: IUsersRepo,
    private val state: MutableStateFlow<AuthenticationState>
) : VModel<Intent, State>(State.Loading("Loading User")) {

    sealed class State {
        class Loading(val msg: String) : State()
        class ShowPicture(val u: User, val viewer: User) : State()
        class EditPhoto(val image: File) : State()
        class Error(val msg: String) : State()
    }

    sealed class Intent {
        class ViewPicture(val user: User) : Intent()
        class UploadPhoto(val user: User, val photo: File) : Intent()
        class EditPhoto(val file: File) : Intent()
    }

    private val viewer get() = state.value.user ?: throw Exception("Can't find viewer's info")

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.UploadPhoto -> uploadPhoto(i)
        is Intent.EditPhoto -> ui.value = State.EditPhoto(i.file)
        is Intent.ViewPicture -> viewPicture(i)
    }

    private fun CoroutineScope.viewPicture(i: Intent.ViewPicture) = launch {
        flow<State> {
            emit(State.ShowPicture(i.user, viewer))
        }.catch {
            emit(State.Error("Failed to show picture"))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.uploadPhoto(i: Intent.UploadPhoto) = launch {
        flow {
            emit(State.Loading("Uploading photo"))
            val user = state.value.user ?: throw Exception("No logged in user")
            repo.uploadPhotoThenReauthenticate(user, i.photo, state).await()
            emit(State.ShowPicture(user, user))
        }.catch {
            emit(State.Error("Failed to upload photo: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }
}