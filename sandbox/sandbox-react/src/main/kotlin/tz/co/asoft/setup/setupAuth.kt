package tz.co.asoft.setup

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.*

fun setupAuth(`package`: String) {
    val roles = InMemoryDao<UserRole>("user-role")
    val claimsDao = InMemoryDao<Claim>("claim")
    val accounts = InMemoryDao<UserAccount>("user-accounts")
    val userService = InMemoryUserFrontEndService(claimsDao, accounts, UsersLocalDao(`package`))
    populateAuthData(roles, userService, accounts, claimsDao)
    Authentication.configure(
        AuthenticationDao(
            users = userService,
            clientApps = InMemoryDao("client-apps"),
            accounts = accounts
        ),
        AuthenticationService(users = userService)
    )

    Authorization.configureDao(AuthorizationDao(claimsDao, roles))
}

private fun Int.show() = if (this < 10) "0$this" else "$this"

private fun populateAuthData(
    roles: IDao<UserRole>,
    userService: InMemoryUserFrontEndService,
    accounts: InMemoryDao<UserAccount>,
    claimsDao: InMemoryDao<Claim>
) = GlobalScope.launch {
    val claim = Claim(permits = listOf(Permit.DEV))
    suspend fun registerUser(num: Int) = userService.register(
        claim = claim,
        accountName = "User Account ${num.show()}",
        userFullname = "Test User ${num.show()}",
        email = Email("account${num.show()}@test.com"),
        phone = Phone("255${(6..7).random()}${num.show().repeat(4)}"),
        password = SHA256.digest(num.show().toByteArray()).hex
    )
    userService.register(
        claim = claim,
        accountName = "User Account One",
        userFullname = "Test User One",
        email = Email("account01@test.com"),
        phone = Phone("255701010101"),
        password = SHA256.digest("01".toByteArray()).hex
    )
    val (_, user2) = userService.register(
        claim = claim,
        userFullname = "Test User Two",
        accountName = "User Account Two",
        email = Email("account02@test.com"),
        phone = Phone("255702020202"),
        password = SHA256.digest("02".toByteArray()).hex
    )

    val accountX = accounts.create(
        UserAccount(
            name = "User Account X",
            scope = "developer",
            claimId = claimsDao.create(claim).uid ?: throw Exception("Failed to register user account with claim(uid=null)")
        )
    )

    userService.add(accountX, user2.uid!!)

    for (i in 3..99) registerUser(i)

    for (i in 1..10) roles.create(UserRole(name = "Role ${i.show()}", permits = listOf()))
}