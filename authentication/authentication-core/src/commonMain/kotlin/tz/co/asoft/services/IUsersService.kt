@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersService : IUsersDao, IUserPhotoUploader {
    suspend fun changePassword(userId: String, oldPass: String, newPass: String): User
    suspend fun register(
        claim: Claim,
        accountName: String,
        userAccountUID: String? = null,
        userFullname: String,
        userUID: String? = null,
        username: String? = null,
        email: Email,
        phone: Phone,
        password: String
    ): Pair<UserAccount, User>

    /**
     * Sign in the respective user/clientApp
     * @return [Either] [User] or <JWT Token> as [String]
     */
    suspend fun signIn(loginId: String, password: String): Either<User, String>?

    /**
     * @return <JWT token> as [String]
     */
    suspend fun authenticate(accountId: String, userId: String): String

    /**
     * This might cause too much traffic
     */
    suspend fun updateLastSeen(userId: String, status: User.Status): User
}