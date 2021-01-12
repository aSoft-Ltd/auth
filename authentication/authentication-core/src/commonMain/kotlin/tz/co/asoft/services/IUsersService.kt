@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.entities.Claim

interface IUsersService : IDao<User>, IUserPhotoUploader {
    val accountsDao: IDao<UserAccount>
    val claimsDao: IDao<Claim>
    fun load(email: Email, pwd: String): Later<User?>
    fun load(phone: Phone, pwd: String): Later<User?>
    fun loadUsers(ua: UserAccount): Later<List<User>>
    fun loadUsersWithOneOf(emails: List<String>, phones: List<String>): Later<List<User>>

    fun changePassword(userId: String, oldPass: String, newPass: String): Later<User>

    /**
     * @return <JWT token> as [String]
     */
    fun authenticate(accountId: String, userId: String): Later<String>

    /**
     * This might cause too much traffic
     */
    fun updateLastSeen(userId: String, status: User.Status): Later<User>
}