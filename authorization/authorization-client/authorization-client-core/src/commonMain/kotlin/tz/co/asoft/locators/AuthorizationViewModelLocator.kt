@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationViewModelLocator(
    private val accountTypes: List<UserAccount.Type>,
    private val repo: AuthorizationRepoLocator
) {
    fun rolesManager(principle: IUserPrinciple) = RolesManagerViewModel(repo.roles, principle, accountTypes.permissionGroups())
    fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
}