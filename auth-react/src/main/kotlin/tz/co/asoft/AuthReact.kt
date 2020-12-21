package tz.co.asoft

import react.RProps

object AuthReact {
    val viewModel = Authentication.viewModels

    object routes {
        val users = "/users"
        val userRoles = "/user-roles"
    }

    val menus = listOf(
        NavMenu("Users", routes.users, FaUserFriends) { true },
        NavMenu("User Roles", routes.userRoles, FaUser) { true }
    )

    val modules: List<AbstractModuleRoute<out RProps>> = listOf(
        ModuleRoute(routes.users, permits = listOf()) { UsersContainer() },
        ModuleRoute(routes.userRoles, permits = listOf()) { RolesContainer() }
    )
}