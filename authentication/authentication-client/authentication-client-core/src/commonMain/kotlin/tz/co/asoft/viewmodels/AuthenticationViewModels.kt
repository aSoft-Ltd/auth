package tz.co.asoft.viewmodels

import tz.co.asoft.*
import tz.co.asoft.repos.AuthenticationRepo

class AuthenticationViewModels(
    private val accountTypes: List<UserAccount.Type>,
    private val repo: AuthenticationRepo
) {
    fun loginForm() = LoginFormViewModel(repo.users)
    fun rolesManager(authState: AuthenticationState.LoggedIn) = RolesManagerViewModel(repo.roles(), authState, accountTypes.permissionGroups())
    fun userDetailsManager() = UserDetailsManagerViewModel(repo.users)
    fun userPermissionsManager() = UserPermissionsManagerViewModel(repo.roles, accountTypes.permissionGroups())
    fun userProfileContainer() = UserProfileContainerViewModel(repo.users)
    fun userProfilePicManager() = UserProfilePicManagerViewModel(repo.users)
    fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
    fun usersManager(principle: IUserPrinciple) = UsersManagerViewModel(repo.users, principle, Authentication.accountTypes)
}