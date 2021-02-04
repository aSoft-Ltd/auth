@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

enum class UserAccountType(
    override val details: String,
    override val permissionGroups: List<SystemPermissionGroup>
) : UserAccount.Type {
    DEVELOPER(
        details = "Accounts dedicated for system developers",
        permissionGroups = universalPermits() + SystemPermissionGroup.DEV
    ),
    ADMIN(
        details = "Accounts dedicated to people who administrate the system",
        permissionGroups = adminPermits() + universalPermits()
    ),
    TESTER(
        details = "Account dedicated to system testers to emulate end users",
        permissionGroups = universalPermits()
    )
}

val permissionGroups = UserAccountType.values().flatMap { it.permissionGroups }
val permits = permissionGroups.flatMap { it.permissions }.map { it.title }