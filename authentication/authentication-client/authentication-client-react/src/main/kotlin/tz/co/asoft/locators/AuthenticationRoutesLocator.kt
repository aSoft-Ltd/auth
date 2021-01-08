@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import react.RProps

class AuthenticationRoutesLocator(
    private val viewModel: AuthenticationViewModelLocator
) {
    object routes {
        val users = "/users"
    }

    fun menus(scope: String) = listOf(
        NavMenu("Users", routes.users, FaUserFriends, scope)
    )

    fun modules(scope: String): List<AbstractModuleRoute<out RProps>> = listOf(
        ModuleRoute(
            path = routes.users,
            permits = User.Permissions.values().map { it.title },
            scope = scope,
            builder = { UsersContainer(locator = viewModel) },
        )
    )
}