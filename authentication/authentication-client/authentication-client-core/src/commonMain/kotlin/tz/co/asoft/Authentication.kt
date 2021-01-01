package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.Authentication.repo.users
import tz.co.asoft.Authorization.repo.roles

object Authentication : DaoAndServiceFactory<AuthenticationDao, AuthenticationService>() {
    @Deprecated("User Permission Groups")
    val systemPermits = mutableSetOf<Permit>()
    val permissionGroups: MutableStateFlow<List<SystemPermissionGroup>> = MutableStateFlow(listOf())
    val state: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Unknown)

    var accountTypes = listOf<UserAccount.Type>()

    object repo {
        fun users() = repo { UsersRepo(service.users) }
        fun accounts() = repo { Repo(dao.accounts) }
        fun clientApps() = repo { Repo(dao.clientApps) }
    }

    object viewModels {
        fun loginForm() = LoginFormViewModel(users())
        fun rolesManager(authState: AuthenticationState.LoggedIn) = RolesManagerViewModel(roles(), authState, permissionGroups.value)
        fun userDetailsManager() = UserDetailsManagerViewModel(users())
        fun userPermissionsManager() = UserPermissionsManagerViewModel(roles(), permissionGroups.value)
        fun userProfileContainer() = UserProfileContainerViewModel(users())
        fun userProfilePicManager() = UserProfilePicManagerViewModel(users())
        fun userRoleManager() = UserRoleManagerViewModel(roles())
        fun usersManager() = UsersManagerViewModel(users(), accountTypes)
    }
}