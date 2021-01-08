@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersFrontendService : IUsersService {
    val localDao: IUsersLocalDao
    override val accountsDao: IDao<UserAccount>
    override val claimsDao: IDao<Claim>
}