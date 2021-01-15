@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun authorizationDao() = AuthorizationDaoLocator(
    ClaimsTestDao(),
    UserRolesTestDao()
)