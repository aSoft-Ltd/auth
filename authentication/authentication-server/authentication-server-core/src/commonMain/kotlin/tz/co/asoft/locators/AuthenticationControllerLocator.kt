@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthenticationServiceLocator

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