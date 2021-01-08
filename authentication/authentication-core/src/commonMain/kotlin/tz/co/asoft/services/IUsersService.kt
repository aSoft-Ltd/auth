@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersService : IUsersDao, IUserPhotoUploader {
    val accountsDao: IDao<UserAccount>
    val claimsDao: IDao<Claim>
    fun changePassword(userId: String, oldPass: String, newPass: String): Later<User>

    /**
     * @return <JWT token> as [String]
     */
    fun authenticate(accountId: String, userId: String): Later<String>

    /**
     * This might cause too much traffic
     */
    fun updateLastSeen(userId: String, status: User.Status): Later<User>
}