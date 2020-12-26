package tz.co.asoft

import tz.co.asoft.utils.createToken

class InMemoryUserFrontEndService(
    val claimsDao: IDao<Claim>,
    override val accountsDao: IDao<UserAccount>,
    override val localDao: IUsersLocalDao,
    private val alg: JWTAlgorithm = HS256Algorithm("secret")
) : IUsersDao by InMemoryUsersDao(), IUsersFrontendService {

    override suspend fun changePassword(userId: String, oldPass: String, newPass: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(accountId: String, userId: String): String {
        val user = load(userId) ?: throw Exception("User with $userId not found")
        val account = user.accounts.find { it.uid == accountId } ?: throw Exception("User ${user.name} doesn't have an account with id $accountId")
        return alg.createToken(account, user)
    }

    override suspend fun updateLastSeen(userId: String, status: User.Status): User {
        TODO("Not yet implemented")
    }

    override suspend fun uploadPhoto(user: User, photo: File): FileRef {
        TODO("Not yet implemented")
    }
}