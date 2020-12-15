@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersDao : IDao<User> {
    suspend fun load(email: Email, pwd: String): User?
    suspend fun load(phone: Phone, pwd: String): User?
    suspend fun loadUsers(ua: UserAccount): List<User>
    suspend fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): List<User>
}