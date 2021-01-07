package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import react.RProps

class AuthModuleState(
    override val accountTypes: List<UserAccount.Type>,
    override val authenticationState: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Unknown),
    override val service: AuthenticationService,
    override val dao: AuthModuleDao
) : IAuthModuleState {
    object routes {
        val users = "/users"
        val userRoles = "/user-roles"
    }

    fun menus(scope: String) = listOf(
        NavMenu("Users", routes.users, FaUserFriends, scope),
        NavMenu("User Roles", routes.userRoles, FaUser, scope)
    )

    fun modules(state: AuthenticationState.LoggedIn, scope: String): List<AbstractModuleRoute<out RProps>> = listOf(
        ModuleRoute(
            path = routes.users,
            permits = User.Permissions.values().map { it.title },
            scope = scope,
            builder = { UsersContainer() },
        ),
        ModuleRoute(
            path = routes.userRoles,
            permits = UserRole.Permissions.values().map { it.title },
            scope = scope,
            builder = { RolesContainer(state, this@AuthModuleState) }
        )
    )
}