package tz.co.asoft

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.builtins.serializer

class KtorUsersFrontendService(
    override val tokenStorage: ITokenStorage,
    override val claimsDao: IDao<Claim>,
    override val accountsDao: IDao<UserAccount>,
    override val options: KtorDaoOptions,
    override val scope: CoroutineScope = CoroutineScope(SupervisorJob()),
    override var token: String?,
    override val client: HttpClient
) : KtorRestDao<User>(options, User.serializer(), scope, "authentication", "users", token, client), IUsersFrontendService {

    private fun load(loginId: String, pwd: String): Later<User?> = scope.later {
        val json = client.post<String>("$path/login") {
            body = MultiPartFormDataContent(formData {
                append("loginId", loginId)
                append("password", pwd)
            })
        }
        Result.parse(User.serializer(), json).response()
    }

    override fun load(email: Email, pwd: String): Later<User?> = load(email.value, pwd)

    override fun load(phone: Phone, pwd: String): Later<User?> = load(phone.value, pwd)

    override fun loadUsers(ua: UserAccount): Later<List<User>> {
        TODO("Not yet implemented")
    }

    override fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): Later<List<User>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(userId: String, oldPass: String, newPass: String): Later<User> {
        TODO("Not yet implemented")
    }

    override fun authenticate(accountId: String, userId: String): Later<String> = scope.later {
        val json = client.post<String>("$path/authenticate") {
            body = MultiPartFormDataContent(formData {
                append("account-id", accountId)
                append("user-id", userId)
            })
        }
        Result.parse(String.serializer(), json).response()
    }

    override fun updateLastSeen(userId: String, status: User.Status): Later<User> {
        TODO("Not yet implemented")
    }

    override fun uploadPhoto(user: User, photo: File): Later<FileRef> {
        TODO("Not yet implemented")
    }
}