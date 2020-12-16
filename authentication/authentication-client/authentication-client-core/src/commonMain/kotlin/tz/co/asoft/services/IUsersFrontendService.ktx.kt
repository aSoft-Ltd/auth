@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun IUsersFrontendService.authenticateThenStoreToken(accountId: String, userId: String): String {
    val token = authenticate(accountId, userId)
    Authentication.state.value = AuthenticationState.LoggedIn(token)
    return localDao.save(token)
}

suspend fun IUsersFrontendService.reAuthenticateIfNeedBe(user: User): String? {
    if (user.uid == Authentication.state.value.user?.uid) {
        val authState = Authentication.state.value
        val userId = authState.user?.uid ?: throw Exception("Current logged in user has null uid")
        val accountId = authState.account?.uid ?: throw Exception("Current logged in account has null uid")
        return authenticateThenStoreToken(accountId, userId)
    }
    return null
}

suspend fun IUsersFrontendService.changePasswordThenStoreToken(userId: String, oldPass: String, newPass: String): User {
    val user = changePassword(userId, oldPass, newPass)
    reAuthenticateIfNeedBe(user)
    return user
}

fun IUsersFrontendService.signOut() = GlobalScope.launch {
    Authentication.state.value = AuthenticationState.LoggedOut
    localDao.delete()
}

suspend fun IUsersFrontendService.editBasicInfoAndReauthenticateIfNeedBe(u: User, name: String, email: Email, phone: Phone): User {
    val user = editBasicInfo(u, name, email, phone)
    reAuthenticateIfNeedBe(user)
    return user
}

/**
 * Returns a [User] if there are multiple accounts this user has signed in to so that the user may specify which account they intend to log in
 * Returns a token if the user has only one account. Meaning, this method automatically signs the user in their single account
 */
suspend fun IUsersFrontendService.signInAndStoreToken(loginId: String, password: String): Either<User, String> {
    val res = signIn(loginId, password) ?: throw Exception("User with those credentials not found")
    val token = res.rightOrNull()
    if (token != null) {
        Authentication.state.value = AuthenticationState.LoggedIn(token)
        localDao.save(token)
    }
    return res
}

suspend fun IUsersFrontendService.uploadPhotoThenReauthenticate(u: User, photo: File): FileRef {
    val fileRef = uploadPhoto(u, photo)
    reAuthenticateIfNeedBe(u)
    return fileRef
}

suspend fun IUsersFrontendService.registerThenSignIn(
    claim: Claim,
    accountName: String,
    userAccountUID: String? = null,
    userFullname: String,
    userUID: String? = null,
    username: String? = null,
    email: Email,
    phone: Phone,
    password: String
): String {
    val (account, user) = register(claim, accountName, userAccountUID, userFullname, userUID, username, email, phone, password)
    val accountId = account.uid ?: throw Exception("Registered account came back a null uid")
    val userId = user.uid ?: throw Exception("Registered user came back with a null uid")
    return authenticateThenStoreToken(accountId, userId)
}