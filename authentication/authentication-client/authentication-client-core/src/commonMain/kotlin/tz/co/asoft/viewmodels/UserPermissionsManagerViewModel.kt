@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.UserPermissionsManagerViewModel.Intent
import tz.co.asoft.UserPermissionsManagerViewModel.State
import tz.co.asoft.entities.UserRole

class UserPermissionsManagerViewModel(
    private val rolesRepo: IRepo<UserRole>,
    private val systemPermissions: List<SystemPermissionGroup>
) : VModel<Intent, State>(State.Loading("Loading")) {
    sealed class State {
        class Loading(val msg: String) : State()
        class ShowPermissions(val u: User, val userPermits: Collection<String>, val systemPermissions: List<SystemPermissionGroup>) : State()
        object ConcealPermissions : State()
        class Error(val msg: String) : State()
        class UserRoleForm(val u: User, val userRole: UserRole?, val roles: List<UserRole>) : State()
    }

    sealed class Intent {
        class UserRoleForm(val u: User) : Intent()
        class Init(val u: User) : Intent()
        class UpdateUserWithRole(val r: UserRole, val u: User) : Intent()
    }

    override fun CoroutineScope.execute(i: Intent): Any = when (i) {
        is Intent.Init -> initiate(i)
        is Intent.UserRoleForm -> userRoleForm(i)
        is Intent.UpdateUserWithRole -> updateUserWithRole(i)
    }

    private fun CoroutineScope.updateUserWithRole(i: Intent.UpdateUserWithRole) = launch {
        flow<State> {
            emit(State.Loading("Changing user's role"))
            TODO("Feature is not yet implemented")
        }.catch {
            emit(State.Error("Failed to change user role: ${it.message}"))
        }.collect {
            ui.value = it
        }
    }

    private fun CoroutineScope.userRoleForm(i: Intent.UserRoleForm) = launch {
        ui.value = State.Loading("Fetching all available roles")
        val roles = rolesRepo.all().await()
        var userRole: UserRole? = null
        var userPermits = ""//i.u.permits.sorted().joinToString()
        for (role in roles) {
            if (role.permits.joinToString() == userPermits) {
                userRole = role
                break
            }
        }
        ui.value = State.UserRoleForm(i.u, userRole, roles)
    }

    private fun initiate(i: Intent.Init) {
        ui.value = State.ShowPermissions(i.u, listOf(), systemPermissions)
        TODO("Fetch user permits here")
//        ui.value = if (userState.user?.hasPermit(":user-roles") == true) {
//            State.ShowPermissions(i.u, systemPermissions)
//        } else State.ConcealPermissions
    }
}