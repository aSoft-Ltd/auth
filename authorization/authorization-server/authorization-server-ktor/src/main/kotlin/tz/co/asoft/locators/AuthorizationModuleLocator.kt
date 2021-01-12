@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationModuleLocator(
    val claims: IRestModule<Claim>,
    val roles: IRestModule<UserRole>
)