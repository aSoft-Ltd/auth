package tz.co.asoft.ui

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.*
import tz.co.asoft.repos.AuthenticationRepo

data class AuthenticationModuleState(
    val accountTypes: List<UserAccount.Type>,
    val authenticationState: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Unknown),
    val service: AuthenticationService,
    val dao: AuthenticationDao
) {
    val repo = AuthenticationRepo(service.users,dao)

    val viewModels = object {
        fun loginForm() = LoginFormViewModel(repo.users())
        fun rolesManager(authState: AuthenticationState.LoggedIn) = RolesManagerViewModel(repo.roles(), authState, accountTypes.permissionGroups())
        fun userDetailsManager() = UserDetailsManagerViewModel(repo.users)
        fun userPermissionsManager() = UserPermissionsManagerViewModel(repo.roles, accountTypes.permissionGroups())
        fun userProfileContainer() = UserProfileContainerViewModel(repo.users)
        fun userProfilePicManager() = UserProfilePicManagerViewModel(repo.users)
        fun userRoleManager() = UserRoleManagerViewModel(repo.roles)
        fun usersManager(principle: IUserPrinciple) = UsersManagerViewModel(repo.users, principle, Authentication.accountTypes)
    }
}