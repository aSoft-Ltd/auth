@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationRepoLocator(
    val users: IUsersRepo,
    val userAccounts: IRepo<UserAccount>,
    val clientApps: IRepo<ClientApp>
) {
    constructor(service: IUsersFrontendService, dao: AuthenticationDaoLocator) : this(
        users = UsersRepo(service),
        userAccounts = Repo(dao.accounts),
        clientApps = Repo(dao.clientApps)
    )
}