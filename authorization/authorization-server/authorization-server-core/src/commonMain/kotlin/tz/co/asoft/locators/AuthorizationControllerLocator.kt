@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationControllerLocator(
    val claims: IRestController<Claim>,
    val roles: IRestController<UserRole>
) {
    constructor(dao: AuthorizationDaoLocator) : this(
        claims = RestController(dao.claims),
        roles = RestController(dao.roles)
    )
}