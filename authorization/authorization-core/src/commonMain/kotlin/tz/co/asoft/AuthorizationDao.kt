@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationDao(
    val claims: IDao<Claim>,
    val roles: IDao<UserRole>
)