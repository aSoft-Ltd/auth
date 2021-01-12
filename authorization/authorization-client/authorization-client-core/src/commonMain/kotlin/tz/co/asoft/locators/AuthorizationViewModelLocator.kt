@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthorizationRepoLocator

class AuthorizationViewModelLocator(
    private val accountTypes: List<UserAccount.Type>,
    private val repo: AuthorizationRepoLocator
) {
    fun rolesManager(principle: IUserPrinciple) = RolesManagerViewModel(repo.roles, principle, accountTypes.permissionGroups())
    fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
}