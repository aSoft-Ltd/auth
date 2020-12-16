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
    sealed class State {
        class Loading(val msg: String) : State()
        class RolePermits(val role: UserRole) : State()
        class RoleForm(val role: UserRole) : State()
        class Error(val msg: String) : State()
    }

    sealed class Intent {
        class ViewRole(val role: UserRole) : Intent()
        class RoleForm(val role: UserRole) : Intent()
        class EditRole(val role: UserRole) : Intent()
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