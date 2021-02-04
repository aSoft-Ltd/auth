@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun UserPrinciple(vararg perms: ISystemPermission) = object : IUserPrinciple {
    override val account: UserAccount = UserAccount(
        name = "User Account 1",
        type = "Testing Account",
        scope = null
    )
    override val user: User = User(
        name = "Test user 1",
        emails = listOf("user1@test.com"),
        phones = listOf(),
        accounts = listOf(account),
        password = "01"
    )
    override val claims = perms.map { it.title to listOf("global") }.toMap()
}