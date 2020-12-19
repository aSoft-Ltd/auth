package tz.co.asoft.setup

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.*

fun setupAuth(`package`: String) {
    val roles = InMemoryDao<UserRole>("user-role")
    val claimsDao = InMemoryDao<Claim>("claim")
    val accounts = InMemoryDao<UserAccount>("user-accounts")
    val userService = InMemoryUserFrontEndService(claimsDao, accounts, UsersLocalDao(`package`))
    populateAuthData(userService, accounts, claimsDao)
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

private fun populateAuthData(
    userService: InMemoryUserFrontEndService,
    accounts: InMemoryDao<UserAccount>,
    claimsDao: InMemoryDao<Claim>
) = GlobalScope.launch {
    val claim = Claim(permits = listOf(Permit.DEV))
    userService.register(
        claim = claim,
        accountName = "User Account One",
        userFullname = "Test User One",
        email = Email("account1@test.com"),
        phone = Phone("255711111111"),
        password = SHA256.digest("1".toByteArray()).hex
    )
    val (_, user2) = userService.register(
        claim = claim,
        userFullname = "Test User Two",
        accountName = "User Account Two",
        email = Email("account2@test.com"),
        phone = Phone("255722222222"),
        password = SHA256.digest("2".toByteArray()).hex
    )

    val accountX = accounts.create(
        UserAccount(
            name = "User Account X",
            claimId = claimsDao.create(claim).uid ?: throw Exception("Failed to register user account with claim(uid=null)")
        )
    )

    userService.add(accountX, user2.uid!!)
}