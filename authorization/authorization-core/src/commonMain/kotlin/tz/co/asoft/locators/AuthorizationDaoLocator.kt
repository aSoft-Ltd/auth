@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationDaoLocator(
    val claims: IDao<Claim>,
    val roles: IDao<UserRole>
)