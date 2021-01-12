@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.locators.AuthorizationRepoLocator

class AuthenticationViewModelLocator(
    private val accountTypes: List<UserAccount.Type>,
    private val authenticationRepo: AuthenticationRepoLocator,
    private val authorizationRepo: AuthorizationRepoLocator,
    private val state: MutableStateFlow<SessionState>
) {
    fun loginForm() = LoginFormViewModel(state, authenticationRepo.users)
    fun userDetailsManager() = UserDetailsManagerViewModel(authenticationRepo.users, state)
    fun userPermissionsManager() = UserPermissionsManagerViewModel(authorizationRepo.roles, accountTypes.permissionGroups())
    fun userProfileContainer() = UserProfileContainerViewModel(authenticationRepo.users, state)
    fun userProfilePicManager() = UserProfilePicManagerViewModel(authenticationRepo.users, state)
    fun usersManager(principle: IUserPrinciple) = UsersManagerViewModel(authenticationRepo.users, principle, accountTypes)
}