package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.Authentication.repo.users
import tz.co.asoft.Authorization.repo.roles

object Authentication : DaoAndServiceFactory<AuthenticationDao, AuthenticationService>() {
    val systemPermits = mutableSetOf<Permit>()
    val state: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Unknown)

    object repo {
        fun users() = repo { UsersRepo(service.users) }
        fun accounts() = repo { Repo(dao.accounts) }
        fun clientApps() = repo { Repo(dao.clientApps) }
    }

    object viewModels {
        fun loginForm() = LoginFormViewModel(users())
        fun rolesManager() = RolesManagerViewModel(roles(), systemPermits)
        fun userDetailsManager() = UserDetailsManagerViewModel(users())
        fun userPermissionsManager() = UserPermissionsManagerViewModel(roles(), systemPermits)
        fun userProfileContainer() = UserProfileContainerViewModel(users())
        fun userProfilePicManager() = UserProfilePicManagerViewModel(users())
        fun userRoleManager() = UserRoleManagerViewModel(roles())
        fun usersManager() = UsersManagerViewModel(users(), roles())
    }
}