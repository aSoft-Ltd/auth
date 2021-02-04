@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.ISystemPermission.Companion.global
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

class RolesManagerViewModel(
    private val repo: IRepo<UserRole>,
    private val principle: IPrinciple,
    private val permissionGroups: List<SystemPermissionGroup>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()

    sealed class State {
        data class Loading(val msg: String) : State()
        data class RoleForm(val role: UserRole?, val permissionGroups: List<SystemPermissionGroup>) : State()
        data class ShowRole(val role: UserRole, val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Roles(val roles: List<UserRole>, val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Error(val exception: Throwable, val origin: Intent) : State() {
            val onRetry = { post(origin) }
        }
    }

    sealed class Intent {
        object LoadRoles : Intent()
        data class CreateRole(val role: UserRole) : Intent()
        data class DeleteRole(val role: UserRole) : Intent()
        data class UpdateRole(val role: UserRole) : Intent()
        class ViewRoleForm(val role: UserRole?) : Intent()
    }

    init {
        observeIntentBus()
        post(Intent.LoadRoles)
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.LoadRoles -> loadRoles(i)
        is Intent.ViewRoleForm -> viewRoleForm(i)
        is Intent.CreateRole -> createRole(i)
        is Intent.DeleteRole -> deleteRole(i)
        is Intent.UpdateRole -> updateRole(i)
    }

    private fun CoroutineScope.updateRole(intent: Intent.UpdateRole) = launch {
        flow {
            require(principle.has(UserRole.Permissions.Update, global)) { "You are not permitted to update a user role" }
            emit(State.Loading("Updating ${intent.role.name}'s role"))
            val role = repo.edit(intent.role).await()
            emit(State.ShowRole(role, permissionGroups))
        }.catch {
            emit(State.Error(Exception("Failed to update user info", it), intent))
        }.collect { ui.value = it }
    }

    private fun CoroutineScope.viewRoleForm(intent: Intent.ViewRoleForm) = launch {
        flow {
            require(principle.has(UserRole.Permissions.Create, global)) { "You are not authorized to create a user role" }
            emit(State.Loading("Preparing form"))
            emit(State.RoleForm(intent.role, permissionGroups))
        }.catch {
            emit(State.Error(Exception("Failed to display role form", it), intent))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.deleteRole(intent: Intent.DeleteRole) = launch {
        flow {
            require(principle.has(UserRole.Permissions.Delete, global)) { "You are not authorized to delete a user role" }
            emit(State.Loading("Deleting ${intent.role.name} role"))
            repo.delete(intent.role).await()
            emit(State.Loading("Role deleted. Loading all roles . . ."))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Exception("Failed to delete role ${intent.role.name}", it), intent))
        }.collect { ui.value = it }
    }

    private fun CoroutineScope.createRole(intent: Intent.CreateRole) = launch {
        flow {
            require(principle.has(UserRole.Permissions.Create, global)) { "You are not authorized to create a user role" }
            emit(State.Loading("Creating role ${intent.role.name}"))
            repo.create(intent.role).await()
            emit(State.Loading("Role created. Loading all roles . . ."))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Exception("Failed to create ${intent.role.name} role", it), intent))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.loadRoles(intent: Intent.LoadRoles) = launch {
        flow {
            require(principle.has(UserRole.Permissions.Read, global)) { "You are not authorized to read user roles" }
            emit(State.Loading("Loading all roles"))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Throwable("Failed to load all roles: ${it.message}", it), origin = intent))
        }.collect {
            ui.value = it
        }
    }
}