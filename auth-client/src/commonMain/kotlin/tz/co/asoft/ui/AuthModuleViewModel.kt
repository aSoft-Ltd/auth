package tz.co.asoft.ui

import tz.co.asoft.*

class AuthModuleViewModel(
    private val accountTypes: List<UserAccount.Type>,
    private val repo: AuthModuleRepo
) {
    fun loginForm() = LoginFormViewModel(repo.users)
    fun rolesManager(principle: IUserPrinciple) = RolesManagerViewModel(repo.roles, principle, accountTypes.permissionGroups())
    fun userDetailsManager() = UserDetailsManagerViewModel(repo.users)
    fun userPermissionsManager() = UserPermissionsManagerViewModel(repo.roles, accountTypes.permissionGroups())
    fun userProfileContainer() = UserProfileContainerViewModel(repo.users)
    fun userProfilePicManager() = UserProfilePicManagerViewModel(repo.users)
    fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
    fun usersManager(principle: IUserPrinciple) = UsersManagerViewModel(repo.users, principle, accountTypes)
}