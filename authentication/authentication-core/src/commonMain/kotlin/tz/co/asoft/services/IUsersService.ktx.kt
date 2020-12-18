@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

suspend fun IUsersService.add(account: UserAccount, userId: String): User {
    val user = load(userId) ?: throw Exception("Failed to load User(uid=$userId)")
    return edit(user.copy(accounts = user.accounts + account))
}