@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationRepoLocator(
    val claims: IRepo<Claim>,
    val roles: IRepo<UserRole>
) {
    constructor(daoLocator: AuthorizationDaoLocator) : this(
        claims = Repo(daoLocator.claims),
        roles = Repo(daoLocator.roles)
    )
}