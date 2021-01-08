package tz.co.asoft

import tz.co.asoft.utils.createToken

class InMemoryUserFrontEndService(
    override val claimsDao: IDao<Claim>,
    override val accountsDao: IDao<UserAccount>,
    override val localDao: IUsersLocalDao,
    private val alg: JWTAlgorithm = HS256Algorithm("secret")
) : IUsersDao by InMemoryUsersDao(), IUsersFrontendService {

    override fun changePassword(userId: String, oldPass: String, newPass: String): Later<User> {
        TODO("Not yet implemented")
    }

    override fun authenticate(accountId: String, userId: String): Later<String> = scope.later {
        val user = load(userId).await() ?: throw Exception("User with $userId not found")
        val account = user.accounts.find { it.uid == accountId } ?: throw Exception("User ${user.name} doesn't have an account with id $accountId")
        val claim = claimsDao.load("$accountId-$userId").await() ?: throw Exception("Failed to get claims for User(name=${user.name})")
        alg.createToken(account, user, claim)
    }

    override fun updateLastSeen(userId: String, status: User.Status): Later<User> {
        TODO("Not yet implemented")
    }

    override fun uploadPhoto(user: User, photo: File): Later<FileRef> {
        TODO("Not yet implemented")
    }
}