@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun IUsersService.load(loginId: String, password: String) = scope.later {
    if (loginId.contains("@")) {
        load(Email(loginId), password).await()
    } else {
        load(Phone(loginId), password).await()
    }
}

/**
 * Sign in the respective user
 * @return [Either] [User] or <JWT Token> as [String]
 */
fun IUsersService.signIn(loginId: String, password: String): Later<Either<User, String>?> = scope.later {
    val user = load(
        loginId = loginId,
        password = password
    ).await() ?: return@later null
    if (user.accounts.size == 1) {
        val userId = user.uid ?: return@later null
        val account = user.accounts.firstOrNull()
        val accountId = account?.uid ?: return@later null
        return@later authenticate(accountId, userId).await().asEither()
    }
    return@later user.asEither()
}

fun IUsersService.register(
    userAccountUID: String? = null,
    accountType: UserAccount.Type,
    accountName: String,
    userFullname: String,
    userUID: String? = null,
    username: String? = null,
    email: Email,
    phone: Phone,
    password: ByteArray
): Later<Pair<UserAccount, User>> = scope.later {
    val account = UserAccount(
        uid = userAccountUID,
        name = accountName,
        type = accountType.name,
        scope = null
    )

    val newAccount: UserAccount = accountsDao.create(account).await()

    val user = User(
        uid = userUID,
        username = username,
        name = userFullname,
        password = SHA256.digest(password).hex,
        emails = listOf(email.value),
        phones = listOf(phone.value),
        accounts = listOf(newAccount)
    )
    val newUser = create(user).await()
    val claim = Claim(
        uid = "${newAccount.uid}-user-${newUser.uid}",
        permits = accountType.permissionGroups.flatMap { it.permissions }.map { it.title }
    )
    claimsDao.create(claim).await()
    newAccount to newUser
}

fun IUsersService.addUserToAccount(account: UserAccount, userId: String): Later<User> = scope.later {
    val user = load(userId).await() ?: throw Exception("Failed to load User(uid=$userId)")
    edit(user.copy(accounts = user.accounts + account)).await()
}