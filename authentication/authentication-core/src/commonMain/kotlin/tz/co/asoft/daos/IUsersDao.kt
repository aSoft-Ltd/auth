@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersDao : IDao<User> {
    fun load(email: Email, pwd: String): Later<User?>
    fun load(phone: Phone, pwd: String): Later<User?>
    fun loadUsers(ua: UserAccount): Later<List<User>>
    fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): Later<List<User>>
}