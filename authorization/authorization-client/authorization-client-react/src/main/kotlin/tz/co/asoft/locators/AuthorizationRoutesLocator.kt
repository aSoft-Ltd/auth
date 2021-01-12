@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import react.RProps
import tz.co.asoft.entities.UserRole

class AuthorizationRoutesLocator(
    private val viewModel: AuthorizationViewModelLocator
) {
    object routes {
        val userRoles = "/user-roles"
    }

    fun menus(scope: String) = listOf(
        NavMenu("User Roles", routes.userRoles, FaUser, scope)
    )

    fun modules(scope: String): List<AbstractModuleRoute<out RProps>> = listOf(
        ModuleRoute(
            path = routes.userRoles,
            permits = UserRole.Permissions.values().map { it.title },
            scope = scope,
            builder = { PrincipleConsumer { RolesContainer(it, locator = viewModel) } }
        )
    )
}