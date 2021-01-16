@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationModuleLocator(
    val users: IRestModule<User>,
    val clientApps: IRestModule<ClientApp>,
    val accounts: IRestModule<UserAccount>
) {
    constructor(keyFetcher: KeyFetcher, controller: AuthenticationControllerLocator) : this(
        users = RestModule(
            version = "v1",
            root = "authentication",
            subRoot = "users",
            keyFetcher = keyFetcher,
            serializer = User.serializer(),
            controller = controller.users,
            readPermission = User.Permissions.Read,
            createPermission = User.Permissions.Create,
            updatePermission = User.Permissions.Update,
            deletePermission = User.Permissions.Delete,
            wipePermission = User.Permissions.Delete
        ),
        clientApps = RestModule(
            version = "v1",
            root = "authentication",
            subRoot = "client-apps",
            keyFetcher = keyFetcher,
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