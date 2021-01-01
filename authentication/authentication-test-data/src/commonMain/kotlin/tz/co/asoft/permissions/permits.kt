@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

object UserAccountType {
    val DEVELOPER = UserAccount.Type(
        name = "System Developer",
        details = "Accounts dedicated for system developers",
        permissionGroups = universalPermits() + SystemPermissionGroup.DEV
    )

    val ADMIN = UserAccount.Type(
        name = "System Administrator",
        details = "Accounts dedicated to people who administrate the system",
        permissionGroups = adminPermits() + universalPermits()
    )

    val TESTER = UserAccount.Type(
        name = "Testers",
        details = "Account dedicated to system testers to emulate end users",
        permissionGroups = universalPermits()
    )

    fun all() = listOf(DEVELOPER, ADMIN, TESTER)

    val permits = all().flatMap { it.permissionGroups }.flatMap { it.permissions }.map { it.name }
}