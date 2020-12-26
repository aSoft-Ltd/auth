@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersService : IUsersDao, IUserPhotoUploader {
    val accountsDao: IDao<UserAccount>
    suspend fun changePassword(userId: String, oldPass: String, newPass: String): User

    /**
     * @return <JWT token> as [String]
     */
    suspend fun authenticate(accountId: String, userId: String): String

    /**
     * This might cause too much traffic
     */
    suspend fun updateLastSeen(userId: String, status: User.Status): User
}