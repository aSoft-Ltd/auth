@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

class RolesManagerViewModel(
    private val repo: IRepo<UserRole>,
    private val permissionGroups: List<SystemPermissionGroup>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()

    sealed class State {
        data class Loading(val msg: String) : State()
        data class RoleForm(val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Roles(val roles: List<UserRole>, val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Error(val msg: String, val cause: Throwable, val origin: Intent) : State() {
            val reason = cause.message
            val stackTrace = cause.stackTraceToString()
            val onCancel = { post(Intent.LoadRoles) }
            val onRetry = { post(origin) }
        }
    }

    sealed class Intent {
        data class CreateRole(val role: UserRole) : Intent()
        data class DeleteRole(val role: UserRole) : Intent()
        object LoadRoles : Intent()
        object NewRoleForm : Intent()
    }

    init {
        observeIntentBus()
        post(Intent.LoadRoles)
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        Intent.LoadRoles -> loadRoles()
        Intent.NewRoleForm -> ui.value = State.RoleForm(permissionGroups)
        is Intent.CreateRole -> createRole(i)
        is Intent.DeleteRole -> deleteRole(i)
    }

    private fun CoroutineScope.deleteRole(i: Intent.DeleteRole) = launch {
        flow {
            emit(State.Loading("Deleting ${i.role.name} role"))
            repo.delete(i.role)
            emit(State.Loading("Role deleted. Loading all roles . . ."))
            emit(State.Roles(repo.all(), permissionGroups))
        }.catch {
            emit(State.Error("Failed to delete role ${i.role.name}", it, i))
        }.collect { ui.value = it }
    }

    private fun CoroutineScope.createRole(i: Intent.CreateRole) = launch {
        flow {
            emit(State.Loading("Creating role ${i.role.name}"))
            repo.create(i.role)
            emit(State.Loading("Role created. Loading all roles . . ."))
            emit(State.Roles(repo.all(), permissionGroups))
        }.catch {
            emit(State.Error("Failed to create role ${i.role.name}", it, i))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.loadRoles() = launch {
        flow {
            emit(State.Loading("Loading all roles"))
            emit(State.Roles(repo.all(), permissionGroups))
        }.catch {
            emit(State.Error("Failed to load all roles: ${it.message}", it, Intent.LoadRoles))
        }.collect {
            ui.value = it
        }
    }
}