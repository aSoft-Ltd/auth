package tz.co.asoft

class InMemoryUserFrontEndService(
    override val claimsDao: IDao<Claim>,
    override val accountsDao: IDao<UserAccount>,
    override val localDao: ITokenStorage,
    private val alg: JWTAlgorithm = HS256Algorithm("secret")
) : IDao<User> by InMemoryDao("user"), IUsersFrontendService {
    override fun load(email: Email, pwd: String) = scope.later {
        all().await().find {
            it.emails.contains(email.value) && it.password == pwd
        }
    }

    override fun load(phone: Phone, pwd: String) = scope.later {
        all().await().find {
            it.phones.contains(phone.value) && it.password == pwd
        }
    }

    override fun loadUsers(ua: UserAccount) = scope.later {
        all().await().filter {
            it.accounts.contains(ua)
        }
    }

    override fun loadUsersWithOneOf(emails: List<String>, phones: List<String>) = scope.later {
        val users = all().await()
        val withEmails = users.filter { emails.any { email -> it.emails.contains(email) } }
        val withPhones = users.filter { phones.any { phone -> it.phones.contains(phone) } }
        withEmails + withPhones
    }

    override fun changePassword(userId: String, oldPass: String, newPass: String): Later<User> {
        TODO("Not yet implemented")
    }

    override fun authenticate(accountId: String, userId: String): Later<String> = scope.later {
        val user = load(userId).await() ?: throw Exception("User with $userId not found")
        val account = user.accounts.find { it.uid == accountId } ?: throw Exception("User ${user.name} doesn't have an account with id $accountId")
        val claim = claimsDao.load(Claim.getUserClaimId(accountId, userId)).await() ?: throw Exception("Failed to get claims for User(name=${user.name})")
        alg.createToken(account, user, claim.permits)
    }

    override fun updateLastSeen(userId: String, status: User.Status): Later<User> {
        TODO("Not yet implemented")
    }

    override fun uploadPhoto(user: User, photo: File): Later<FileRef> {
        TODO("Not yet implemented")
    }
}