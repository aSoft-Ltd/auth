@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun universalPermits() = listOf(
    SystemPermissionGroup(
        name = "Account",
        details = "Scopes for managing your own account",
        permissions = setOf(
            SystemPermission(
                name = "account.users.read",
                details = "Grants access to read sub users for this particular account",
            ),
            SystemPermission(
                name = "account.users.create",
                details = "Grants access to create sub users",
                needs = listOf("accounts.users.read")
            ),
            SystemPermission(
                name = "account.users.update",
                details = "Grants access to update sub users information",
                needs = listOf("accounts.users.read")
            ),
            SystemPermission(
                name = "account.users.delete",
                details = "Grants access to delete sub users information",
                needs = listOf("accounts.users.read")
            ),
            SystemPermission(
                name = "account.clients.read",
                details = "Grants access to read client apps for this particular Account",
            ),
            SystemPermission(
                name = "account.clients.create",
                details = "Grants access to create client apps for this particular Account",
            ),
            SystemPermission(
                name = "account.client.update",
                details = "Grants access to update client apps info for this particular Account"
            ),
            SystemPermission(
                name = "account.client.delete",
                details = "Grants access to delete client apps info for this particular Account"
            )
        )
    ),
    SystemPermissionGroup(
        name = "Profile",
        details = "Scopes for managing your profile",
        permissions = setOf()
    )
)