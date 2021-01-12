@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.entities.Claim

interface IUsersFrontendService : IUsersService {
    val localDao: IUsersLocalDao
    override val accountsDao: IDao<UserAccount>
    override val claimsDao: IDao<Claim>
}