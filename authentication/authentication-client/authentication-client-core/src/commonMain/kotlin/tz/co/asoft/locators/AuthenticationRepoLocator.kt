@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthenticationFrontendServiceLocator

class AuthenticationRepoLocator(
    val users: IUsersRepo,
    val userAccounts: IRepo<UserAccount>,
    val clientApps: IRepo<ClientApp>
) {
    constructor(service: AuthenticationFrontendServiceLocator) : this(
        users = UsersRepo(service.users),
        userAccounts = Repo(service.accounts),
        clientApps = Repo(service.clientApps)
    )
}