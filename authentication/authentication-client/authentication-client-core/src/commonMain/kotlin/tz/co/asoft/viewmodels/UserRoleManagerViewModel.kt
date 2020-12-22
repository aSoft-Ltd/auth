@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.UserRoleManagerViewModel.Intent
import tz.co.asoft.UserRoleManagerViewModel.State

class UserRoleManagerViewModel(
    private val repo: IRepo<UserRole>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()
    sealed class State {
        data class Loading(val msg: String) : State()
        data class RolePermits(val role: UserRole) : State() {
            val onEdit = { post(Intent.EditRole(role)) }
            val onDelete = { RolesManagerViewModel.post(RolesManagerViewModel.Intent.DeleteRole(role)) }
        }
        data class RoleForm(val role: UserRole) : State()
        data class Error(val msg: String) : State()
    }

    sealed class Intent {
        data class ViewRole(val role: UserRole) : Intent()
        data class RoleForm(val role: UserRole) : Intent()
        data class EditRole(val role: UserRole) : Intent()
    }

    init {
        launch { collect { post(it) } }
    }

    override fun execute(i: Intent): Any = when (i) {
        is Intent.ViewRole -> ui.value = State.RolePermits(i.role)
        is Intent.RoleForm -> ui.value = State.RoleForm(i.role)
        is Intent.EditRole -> editRole(i)
    }

    private fun editRole(i: Intent.EditRole) = launch {
        flow {
            emit(State.Loading("Editing role ${i.role.name}"))
            emit(State.RolePermits(repo.edit(i.role)))
        }.catch {
            emit(State.Error("Failed to edit role"))
        }.collect {
            ui.value = it
        }
    }
}