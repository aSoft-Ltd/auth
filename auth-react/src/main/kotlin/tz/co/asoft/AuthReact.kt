package tz.co.asoft

import react.RProps

object AuthReact {
    val viewModel = Authentication.viewModels

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
            permits = listOf(),
            scope = scope,
            builder = { UsersContainer() },
        ),
        ModuleRoute(
            path = routes.userRoles,
            permits = listOf(
                Permit("authorization", "roles", "create", "*").toString()
            ),
            scope = scope,
            builder = { RolesContainer(state) }
        )
    )
}