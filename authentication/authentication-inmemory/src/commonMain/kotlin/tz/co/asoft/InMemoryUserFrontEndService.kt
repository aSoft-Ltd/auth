package tz.co.asoft

import kotlinx.serialization.json.Json

class InMemoryUserFrontEndService(
    val claimsDao: IDao<Claim>,
    val accountsDao: IDao<UserAccount>,
    override val localDao: IUsersLocalDao,
    private val alg: JWTAlgorithm = HS256Algorithm("secret")
) : IUsersDao by InMemoryUsersDao(), IUsersFrontendService {

    override suspend fun changePassword(userId: String, oldPass: String, newPass: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        claim: Claim,
        accountName: String,
        userAccountUID: String?,
        userFullname: String,
        userUID: String?,
        username: String?,
        email: Email,
        phone: Phone,
        password: String
    ): Pair<UserAccount, User> {
        val account = UserAccount(
            name = accountName,
            type = "client",
            scope = null,
            claimId = claimsDao.create(claim).uid ?: throw Exception("Failed to register user account with claim(uid=null)")
        )

        val newAccount: UserAccount = accountsDao.create(account)

        val user = User(
            uid = userUID,
            username = username,
            name = userFullname,
            password = password,
            emails = listOf(email.value),
            phones = listOf(phone.value),
            claimId = claimsDao.create(claim).uid ?: throw Exception("Failed to register user account with claim(uid=null)"),
            accounts = listOf(newAccount)
        )
        return newAccount to create(user)
    }

    override suspend fun signIn(loginId: String, password: String): Either<User, String>? {
        val user = all().find { it.emails.contains(loginId) && it.password == password } ?: return null;
        if (user.accounts.size == 1) {
            val userId = user.uid ?: return null
            val account = user.accounts.first()
            val accountId = account.uid ?: return null
            return authenticate(accountId, userId).asEither()
        }
        return user.asEither()
    }

    override suspend fun authenticate(accountId: String, userId: String): String {
        val user = all().find { it.uid == userId } ?: throw Exception("User with $userId not found")
        val account = user.accounts.find { it.uid == accountId } ?: throw Exception("User ${user.name} doesn't have an account with id $accountId")

        val jwt = alg.createJWT {
            uid = userId
            userName = user.username ?: user.name
            accountName = account.name
            aid = accountId
            claimId = user.claimId
            claims = listOf("test")
            put("user", Mapper.decodeFromString(Json.encodeToString(User.serializer(), user)))
            put("account", Mapper.decodeFromString(Json.encodeToString(UserAccount.serializer(), account)))
        }
        return jwt.token()
    }

    override suspend fun updateLastSeen(userId: String, status: User.Status): User {
        TODO("Not yet implemented")
    }

    override suspend fun uploadPhoto(user: User, photo: File): FileRef {
        TODO("Not yet implemented")
    }
}