package tz.co.asoft.repos

import tz.co.asoft.*

class AuthenticationRepo(
    val users: IUsersRepo,
    val userAccounts: IRepo<UserAccount>,
    val clientApps: IRepo<ClientApp>
) {
    constructor(service: IUsersFrontendService, dao: AuthenticationDao) : this(
        users = UsersRepo(service),
        userAccounts = Repo(dao.accounts),
        clientApps = Repo(dao.clientApps)
    )
}