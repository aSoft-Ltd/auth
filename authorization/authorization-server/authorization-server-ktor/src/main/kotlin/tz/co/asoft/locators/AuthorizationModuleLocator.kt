@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationModuleLocator(
    val claims: IRestModule<Claim>,
    val roles: IRestModule<UserRole>
) {
    constructor(keyFetcher: KeyFetcher, controller: AuthorizationControllerLocator) : this(
        claims = RestModule(
            version = "v1",
            root = "authorization",
            subRoot = "claims",
            keyFetcher = keyFetcher,
            serializer = Claim.serializer(),
            controller = controller.claims,
            readPermission = Claim.Permissions.Read,
            createPermission = Claim.Permissions.Create,
            updatePermission = Claim.Permissions.Update,
            deletePermission = Claim.Permissions.Delete,
            wipePermission = Claim.Permissions.Wipe
        ),
        roles = RestModule(
            version = "v1",
            root = "authorization",
            subRoot = "roles",
            keyFetcher = keyFetcher,
            serializer = UserRole.serializer(),
            controller = controller.roles,
            readPermission = UserRole.Permissions.Read,
            createPermission = UserRole.Permissions.Create,
            updatePermission = UserRole.Permissions.Update,
            deletePermission = UserRole.Permissions.Delete,
            wipePermission = UserRole.Permissions.Wipe
        )
    )
}