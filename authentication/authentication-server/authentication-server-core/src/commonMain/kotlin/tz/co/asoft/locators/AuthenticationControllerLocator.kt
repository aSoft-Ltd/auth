@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationControllerLocator(
    val users: IRestController<User>,
    val clientApps: IRestController<ClientApp>,
    val accounts: IRestController<UserAccount>
) {
    constructor(dao: AuthenticationServiceLocator) : this(
        users = RestController(dao.users),
        clientApps = RestController(dao.clientApps),
        accounts = RestController(dao.accounts)
    )
}