@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationControllerLocator(
    val users: IUsersController,
    val clientApps: IRestController<ClientApp>,
    val accounts: IRestController<UserAccount>
) {
    constructor(dao: AuthenticationServiceLocator) : this(
        users = UsersController(dao.users),
        clientApps = RestController(dao.clientApps),
        accounts = RestController(dao.accounts)
    )
}