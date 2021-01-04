package tz.co.asoft.ui

import tz.co.asoft.*

class AuthModuleRepo(
    val users: IUsersRepo,
    val userAccounts: IRepo<UserAccount>,
    val clientApps: IRepo<ClientApp>,
    val claims: IRepo<Claim>,
    val roles: IRepo<UserRole>
) {
    constructor(service: IUsersFrontendService, dao: AuthModuleDao) : this(
        users = UsersRepo(service),
        userAccounts = Repo(dao.accounts),
        clientApps = Repo(dao.clientApps),
        claims = Repo(dao.claims),
        roles = Repo(dao.roles)
    )
}