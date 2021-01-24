@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersFrontendService : IUsersService, IClientUserPhotoUploader {
    val tokenStorage: ITokenStorage
    override val accountsDao: IDao<UserAccount>
    override val claimsDao: IDao<Claim>
}