package tz.co.asoft

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class KtorUsersFrontendService(
    override val localDao: IUsersLocalDao,
    override val claimsDao: IDao<Claim>,
    override val accountsDao: IDao<UserAccount>,
    override val options: RestfulOptions,
    override val scope: CoroutineScope = CoroutineScope(SupervisorJob()),
    override var token: String?,
    override val client: HttpClient
) : RestfulDao<User>(options, User.serializer(), scope, "authentication", "users", token, client), IUsersFrontendService {
    override fun load(email: Email, pwd: String): Later<User?> {
        TODO("Not yet implemented")
    }

    override fun load(phone: Phone, pwd: String): Later<User?> {
        TODO("Not yet implemented")
    }

    override fun loadUsers(ua: UserAccount): Later<List<User>> {
        TODO("Not yet implemented")
    }

    override fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): Later<List<User>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(userId: String, oldPass: String, newPass: String): Later<User> {
        TODO("Not yet implemented")
    }

    override fun authenticate(accountId: String, userId: String): Later<String> {
        TODO("Not yet implemented")
    }

    override fun updateLastSeen(userId: String, status: User.Status): Later<User> {
        TODO("Not yet implemented")
    }

    override fun uploadPhoto(user: User, photo: File): Later<FileRef> {
        TODO("Not yet implemented")
    }
}