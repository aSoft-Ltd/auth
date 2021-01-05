package tz.co.asoft.ui

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.*

class AuthModuleViewModel(
    private val accountTypes: List<UserAccount.Type>,
    private val repo: AuthModuleRepo,
    private val state: MutableStateFlow<AuthenticationState>
) {
    fun loginForm() = LoginFormViewModel(repo.users)
    fun rolesManager(principle: IUserPrinciple) = RolesManagerViewModel(repo.roles, principle, accountTypes.permissionGroups())
    fun userDetailsManager() = UserDetailsManagerViewModel(repo.users, state)
    fun userPermissionsManager() = UserPermissionsManagerViewModel(repo.roles, accountTypes.permissionGroups())
    fun userProfileContainer() = UserProfileContainerViewModel(repo.users, state)
    fun userProfilePicManager() = UserProfilePicManagerViewModel(repo.users, state)
    fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
    fun usersManager(principle: IUserPrinciple) = UsersManagerViewModel(repo.users, principle, accountTypes)
}