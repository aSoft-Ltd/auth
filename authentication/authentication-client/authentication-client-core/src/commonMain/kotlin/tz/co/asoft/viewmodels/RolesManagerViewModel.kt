@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

class RolesManagerViewModel(
    private val repo: IRepo<UserRole>,
    private val systemPermits: Set<Permit>
) : VModel<Intent, State>(State.Loading("Loading")) {
    companion object : IntentBus<Intent>()

    sealed class State {
        data class Loading(val msg: String) : State()
        data class RoleForm(val systemPermits: Set<Permit>) : State()
        data class Roles(val roles: List<UserRole>, val systemPermits: Set<Permit>) : State()
        data class Error(val msg: String) : State()
    }

    sealed class Intent {
        data class CreateRole(val role: UserRole) : Intent()
        data class DeleteRole(val role: UserRole) : Intent()
        object LoadRoles : Intent()
        object NewRoleForm : Intent()
    }

    init {
        launch { collect { post(it) } }
    }

    override fun execute(i: Intent): Any = when (i) {
        Intent.LoadRoles -> loadRoles()
        Intent.NewRoleForm -> ui.value = State.RoleForm(systemPermits)
        is Intent.CreateRole -> createRole(i)
        is Intent.DeleteRole -> deleteRole(i)
    }

    private fun deleteRole(i: Intent.DeleteRole) = launch {
        flow {
            emit(State.Loading("Deleting ${i.role.name} role"))
            repo.delete(i.role)
            emit(State.Loading("Role deleted. Loading all roles . . ."))
            emit(State.Roles(repo.all(), systemPermits))
        }.catch {
            emit(State.Error("Failed to delete role ${i.role.name}: ${it.message}"))
        }.collect { ui.value = it }
    }

    private fun createRole(i: Intent.CreateRole) = launch {
        flow {
            emit(State.Loading("Creating role ${i.role.name}"))
            repo.create(i.role)
            emit(State.Loading("Role created. Loading all roles . . ."))
            emit(State.Roles(repo.all(),systemPermits))
        }.catch {
            emit(State.Error("Failed to create role ${i.role.name}: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }

    private fun loadRoles(): Any = launch {
        flow {
            emit(State.Loading("Loading all roles"))
            emit(State.Roles(repo.all(),systemPermits))
        }.catch {
            emit(State.Error("Failed to load all roles: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }
}