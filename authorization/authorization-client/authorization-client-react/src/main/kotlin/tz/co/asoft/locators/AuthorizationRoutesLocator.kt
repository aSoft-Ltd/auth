package tz.co.asoft.locators

import react.RProps
import tz.co.asoft.*

class AuthorizationRoutesLocator(
    private val viewModel: AuthorizationViewModelLocator
) {
    object routes {
        val userRoles = "/user-roles"
    }

    fun menus(scope: String) = listOf(
        NavMenu("User Roles", routes.userRoles, FaUser, scope)
    )

    fun modules(principle: IUserPrinciple, scope: String): List<AbstractModuleRoute<out RProps>> = listOf(
        ModuleRoute(
            path = routes.userRoles,
            permits = UserRole.Permissions.values().map { it.title },
            scope = scope,
            builder = { RolesContainer(principle, locator = viewModel) }
        )
    )
}