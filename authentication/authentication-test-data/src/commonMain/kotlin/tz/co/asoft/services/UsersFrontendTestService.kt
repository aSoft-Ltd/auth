@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.daos.ClaimsTestDao
import tz.co.asoft.daos.UserAccountsTestDao

class UsersFrontendTestService(
    claimDao: IDao<Claim> = ClaimsTestDao(),
    accountsDao: IDao<UserAccount> = UserAccountsTestDao(),
) : IUsersFrontendService by InMemoryUserFrontEndService(claimDao, accountsDao, InMemoryUsersLocalDao()) {

    fun populate() = scope.later {
        register(
            accountName = "User Account One",
            userFullname = "Test User One",
            accountType = "system.admin",
            email = Email("account01@test.com"),
            phone = Phone("255701010101"),
            password = SHA256.digest("01".toByteArray()).hex
        ).await()

        val (_, user2) = register(
            userFullname = "Test User Two",
            accountName = "User Account Two",
            accountType = "system.admin",
            email = Email("account02@test.com"),
            phone = Phone("255702020202"),
            password = SHA256.digest("02".toByteArray()).hex
        ).await()

        val accountX = accountsDao.create(
            UserAccount(
                name = "User Account X",
                type = "system.admin",
                scope = null
            )
        ).await()

        addUserToAccount(accountX, user2.uid!!).await()

        for (i in 3..99) registerUser(i).await()
    }

    private fun Int.show() = if (this < 10) "0$this" else "$this"

    fun registerUser(num: Int) = register(
        accountName = "User Account ${num.show()}",
        userFullname = "Test User ${num.show()}",
        accountType = "guest",
        email = Email("account${num.show()}@test.com"),
        phone = Phone("255${(6..7).random()}${num.show().repeat(4)}"),
        password = SHA256.digest(num.show().toByteArray()).hex
    )
}