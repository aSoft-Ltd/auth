@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationModuleLocator(
    val claims: IRestModule<Claim>,
    val roles: IRestModule<UserRole>
) {
    constructor(keyFetcher: KeyFetcher, verifier: (SecurityKey) -> JWTVerifier, controller: AuthorizationControllerLocator) : this(
        claims = ClaimsModule(
            version = "v1",
            keyFetcher = keyFetcher,
            verifier = verifier,
            controller = controller.claims
        ),
        roles = UserRolesModule(
            version = "v1",
            keyFetcher = keyFetcher,
            verifier = verifier,
            controller = controller.roles
        )
    )
}