@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun adminPermits() = listOf(
    SystemPermissionGroup(
        name = "authentication",
        details = "Scopes for managing all users",
        permissions = setOf(
            SystemPermission(
                name = "authentication.users.read",
                details = "Grants access to read all users in the system"
            ),
            SystemPermission(
                name = "authentication.users.create",
                details = "Grants access to create new users for the system",
                needs = listOf("authentication.users.read")
            ),
            SystemPermission(
                name = "authentication.users.update",
                details = "Grants access to change users info (including their passwords)",
                needs = listOf("authentication.users.read")
            ),
            SystemPermission(
                name = "authentication.users.delete",
                details = "Grants access to delete a specific user",
                needs = listOf("authentication.users.read")
            ),
            SystemPermission(
                name = "authentication.accounts.read",
                details = "Grants access to view all user accounts information"
            ),
            SystemPermission(
                name = "authentication.accounts.create",
                details = "Grants access to create new user accounts",
                needs = listOf("authentication.accounts.read")
            ),
            SystemPermission(
                name = "authentication.accounts.update",
                details = "Grants access to edit existing user accounts information",
                needs = listOf("authentication.accounts.read")
            ),
            SystemPermission(
                name = "authentication.accounts.delete",
                details = "Grants access to delete user accounts",
                needs = listOf("authentication.accounts.read")
            ),
            SystemPermission(
                name = "authentication.clients.read",
                details = "Grants access to be able to read all client apps"
            ),
            SystemPermission(
                name = "authentication.clients.create",
                details = "Grants access to create client apps for integration",
                needs = listOf("authentication.clients.read")
            ),
            SystemPermission(
                name = "authentication.clients.update",
                details = "Grants access to edit client apps information",
                needs = listOf("authentication.clients.read")
            ),
            SystemPermission(
                name = "authentication.clients.delete",
                details = "Grants access to delete client apps information",
                needs = listOf("authentication.clients.read")
            )
        )
    ),
    SystemPermissionGroup(
        name = "authorization",
        details = "Scopes for managing user roles",
        permissions = UserRole.Permissions.values().toSet()
    )
)