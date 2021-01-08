@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationRepoLocator(
    val users: IUsersRepo,
    val userAccounts: IRepo<UserAccount>,
    val clientApps: IRepo<ClientApp>
) {
    constructor(service: AuthenticationServiceLocator) : this(
        users = UsersRepo(service.users),
        userAccounts = Repo(service.accounts),
        clientApps = Repo(service.clientApps)
    )
}