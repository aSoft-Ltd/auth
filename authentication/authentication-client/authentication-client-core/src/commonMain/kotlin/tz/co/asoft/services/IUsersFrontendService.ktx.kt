@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.launch

fun IUsersFrontendService.authenticateLocallyOrLogout() {
    val token = localDao.load()
    Authentication.state.value = if (token == null) {
        AuthenticationState.LoggedOut
    } else {
        AuthenticationState.LoggedIn(token)
    }
}

fun IUsersFrontendService.authenticateThenStoreToken(accountId: String, userId: String): Later<String> = scope.later {
    val token = authenticate(accountId, userId).await()
    useToken(token)
}

fun IUsersFrontendService.reAuthenticateIfNeedBe(user: User): Later<String?> = scope.later {
    if (user.uid == Authentication.state.value.user?.uid) {
        val authState = Authentication.state.value
        val userId = authState.user?.uid ?: throw Exception("Current logged in user has null uid")
        val accountId = authState.account?.uid ?: throw Exception("Current logged in account has null uid")
        authenticateThenStoreToken(accountId, userId).await()
    }
    null
}

fun IUsersFrontendService.changePasswordThenStoreToken(
    userId: String,
    oldPass: String,
    newPass: String
) = scope.later {
    val user = changePassword(userId, oldPass, newPass).await()
    reAuthenticateIfNeedBe(user).await()
    user
}

fun IUsersFrontendService.signOut() {
    Authentication.state.value = AuthenticationState.LoggedOut
    localDao.delete()
}

fun IUsersFrontendService.editBasicInfoAndReauthenticateIfNeedBe(
    u: User,
    name: String,
    email: Email,
    phone: Phone
) = scope.later {
    val user = editBasicInfo(u, name, email, phone).await()
    reAuthenticateIfNeedBe(user).await()
    user
}

/**
 * Returns a [User] if there are multiple accounts this user has signed in to so that the user may specify which account they intend to log in
 * Returns a token if the user has only one account. Meaning, this method automatically signs the user in their single account
 */
fun IUsersFrontendService.signInAndStoreToken(loginId: String, password: String): Later<Either<User, String>> = scope.later {
    val res = signIn(loginId, password).await() ?: throw Exception("User with those credentials not found")
    when (val value = res.value) {
        is String -> useToken(value)
        is User -> Unit
        else -> throw Exception("Failed to sign you in")
    }
    res
}

fun IUsersFrontendService.useToken(token: String): String {
    Authentication.state.value = AuthenticationState.LoggedIn(token)
    return localDao.save(token)
}

fun IUsersFrontendService.uploadPhotoThenReauthenticate(u: User, photo: File): Later<FileRef> = scope.later {
    val fileRef = uploadPhoto(u, photo).await()
    reAuthenticateIfNeedBe(u).await()
    fileRef
}

fun IUsersFrontendService.registerThenSignIn(
    userAccountUID: String? = null,
    accountType: String,
    accountName: String,
    userFullname: String,
    userUID: String? = null,
    username: String? = null,
    email: Email,
    phone: Phone,
    password: String
): Later<String> = scope.later {
    val (account, user) = register(userAccountUID, accountType, accountName, userFullname, userUID, username, email, phone, password).await()
    val accountId = account.uid ?: throw Exception("Registered account came back a null uid")
    val userId = user.uid ?: throw Exception("Registered user came back with a null uid")
    authenticateThenStoreToken(accountId, userId).await()
}