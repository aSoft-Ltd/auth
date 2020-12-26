@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlin.jvm.JvmName

suspend fun IUsersService.load(loginId: String, password: String) = if (loginId.contains("@")) {
    load(Email(loginId), password)
} else {
    load(Phone(loginId), password)
}

/**
 * Sign in the respective user
 * @return [Either] [User] or <JWT Token> as [String]
 */
suspend fun IUsersService.signIn(loginId: String, password: String): Either<User, String>? {
    val user = load(loginId = loginId, password = password) ?: throw RuntimeException("User(loginId=$loginId) and provided password not found")
    if (user.accounts.size == 1) {
        val userId = user.uid ?: return null
        val account = user.accounts.firstOrNull()
        val accountId = account?.uid ?: return null
        return authenticate(accountId, userId).asEither()
    }
    return user.asEither()
}

suspend fun IUsersService.register(
    userAccountUID: String? = null,
    accountType: String,
    accountName: String,
    userFullname: String,
    userUID: String? = null,
    username: String? = null,
    email: Email,
    phone: Phone,
    password: String
): Pair<UserAccount, User> {
    val account = UserAccount(
        uid = userAccountUID,
        name = accountName,
        type = accountType,
        scope = null
    )

    val newAccount: UserAccount = accountsDao.create(account)

    val user = User(
        uid = userUID,
        username = username,
        name = userFullname,
        password = password,
        emails = listOf(email.value),
        phones = listOf(phone.value),
        accounts = listOf(newAccount)
    )
    return newAccount to create(user)
}

suspend fun IUsersService.addUserToAccount(account: UserAccount, userId: String): User {
    val user = load(userId) ?: throw Exception("Failed to load User(uid=$userId)")
    return edit(user.copy(accounts = user.accounts + account))
}