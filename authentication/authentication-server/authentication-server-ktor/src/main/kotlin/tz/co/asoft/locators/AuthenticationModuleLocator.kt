@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationModuleLocator(
    val users: IRestModule<User>,
    val clientApps: IRestModule<ClientApp>,
    val accounts: IRestModule<UserAccount>
) {
    constructor(keyFetcher: KeyFetcher, verifier: (SecurityKey) -> JWTVerifier, controller: AuthenticationControllerLocator) : this(
        users = UsersModule(
            version = "v1",
            controller = controller.users,
            fetcher = keyFetcher,
            verifier=verifier
        ),
        clientApps = RestModule(
            version = "v1",
            root = "authentication",
            subRoot = "client-apps",
            keyFetcher = keyFetcher,
            verifier = verifier,
            serializer = ClientApp.serializer(),
            controller = controller.clientApps,
            readPermission = User.Permissions.Read,
            createPermission = User.Permissions.Create,
            updatePermission = User.Permissions.Update,
            deletePermission = User.Permissions.Delete,
            wipePermission = User.Permissions.Delete
        ),
        accounts = RestModule(
            version = "v1",
            root = "authentication",
            subRoot = "accounts",
            keyFetcher = keyFetcher,
            verifier = verifier,
            serializer = UserAccount.serializer(),
            controller = controller.accounts,
            readPermission = User.Permissions.Read,
            createPermission = User.Permissions.Create,
            updatePermission = User.Permissions.Update,
            deletePermission = User.Permissions.Delete,
            wipePermission = User.Permissions.Delete
        )
    )
}