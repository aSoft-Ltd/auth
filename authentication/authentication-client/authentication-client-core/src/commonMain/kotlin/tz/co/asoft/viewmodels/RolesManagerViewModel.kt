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
    private val principle: IUserPrinciple,
    private val permissionGroups: List<SystemPermissionGroup>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()

    sealed class State {
        data class Loading(val msg: String) : State()
        data class RoleForm(val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Roles(val roles: List<UserRole>, val permissionGroups: List<SystemPermissionGroup>) : State()
        data class Error(val cause: Throwable, val origin: Intent) : State() {
            val onCancel = { post(Intent.LoadRoles) }
            val onRetry = { post(origin) }
        }

        val onCreateRole get() = { post(Intent.NewRoleForm) }.takeIf { canCreateRole() }

        companion object {
            var principle: IUserPrinciple? = null
            fun canCreateRole() = principle?.has("authentication.roles.create") == true
        }
    }

    sealed class Intent {
        data class CreateRole(val role: UserRole) : Intent()
        data class DeleteRole(val role: UserRole) : Intent()
        object LoadRoles : Intent()
        object NewRoleForm : Intent()
    }

    init {
        State.principle = principle
        observeIntentBus()
        post(Intent.LoadRoles)
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.LoadRoles -> loadRoles(i)
        is Intent.NewRoleForm -> newRoleForm(i)
        is Intent.CreateRole -> createRole(i)
        is Intent.DeleteRole -> deleteRole(i)
    }

    private fun CoroutineScope.newRoleForm(intent: Intent.NewRoleForm) = launch {
        flow {
            emit(State.Loading("Preparing form"))
            require(State.canCreateRole()) { "You are not authorized to create a role" }
            emit(State.RoleForm(permissionGroups))
        }.catch {
            emit(State.Error(Exception("Failed to display role form", it), intent))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.deleteRole(intent: Intent.DeleteRole) = launch {
        flow {
            emit(State.Loading("Deleting ${intent.role.name} role"))
            repo.delete(intent.role).await()
            emit(State.Loading("Role deleted. Loading all roles . . ."))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Throwable("Failed to delete role ${intent.role.name}", it), intent))
        }.collect { ui.value = it }
    }

    private fun CoroutineScope.createRole(intent: Intent.CreateRole) = launch {
        flow {
            require(State.canCreateRole()) { "You are not authorized to create a role" }
            emit(State.Loading("Creating role ${intent.role.name}"))
            repo.create(intent.role).await()
            emit(State.Loading("Role created. Loading all roles . . ."))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Throwable("Failed to create role ${intent.role.name}", it), intent))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.loadRoles(intent: Intent.LoadRoles) = launch {
        flow {
            emit(State.Loading("Loading all roles"))
            emit(State.Roles(repo.all().await(), permissionGroups))
        }.catch {
            emit(State.Error(Throwable("Failed to load all roles: ${it.message}", it), origin = intent))
        }.collect {
            ui.value = it
        }
    }
}