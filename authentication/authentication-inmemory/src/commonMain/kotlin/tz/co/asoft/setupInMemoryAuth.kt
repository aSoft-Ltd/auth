package tz.co.asoft

import kotlinx.coroutines.GlobalScope

fun setupInMemoryAuth(localDao: IUsersLocalDao): Later<Unit> {
    val roles = UniqueNameInMemoryDao<UserRole>("user-role")
    val claimsDao = InMemoryDao<Claim>("claim")
    val accounts = InMemoryDao<UserAccount>("user-accounts")
    val userService = InMemoryUserFrontEndService(claimsDao, accounts, localDao)
    val later = populateAuthData(roles, userService, accounts, claimsDao)
    Authentication.configure(
        AuthenticationDao(
            users = userService,
            clientApps = InMemoryDao("client-apps"),
            accounts = accounts
        ),
        AuthenticationService(users = userService)
    )

    Authorization.configureDao(AuthorizationDao(claimsDao, roles))
    return later
}

private fun Int.show() = if (this < 10) "0$this" else "$this"

private fun populateAuthData(
    roles: IDao<UserRole>,
    userService: InMemoryUserFrontEndService,
    accounts: InMemoryDao<UserAccount>,
    claimsDao: InMemoryDao<Claim>
) = GlobalScope.later {
    //    val claim = Claim(permits = SystemPermission.DEV.permissions.toList())
    fun registerUser(num: Int) = userService.register(
        accountName = "User Account ${num.show()}",
        userFullname = "Test User ${num.show()}",
        accountType = "guest",
        email = Email("account${num.show()}@test.com"),
        phone = Phone("255${(6..7).random()}${num.show().repeat(4)}"),
        password = SHA256.digest(num.show().toByteArray()).hex
    )

    userService.register(
        accountName = "User Account One",
        userFullname = "Test User One",
        accountType = "system.admin",
        email = Email("account01@test.com"),
        phone = Phone("255701010101"),
        password = SHA256.digest("01".toByteArray()).hex
    ).await()

    val (_, user2) = userService.register(
        userFullname = "Test User Two",
        accountName = "User Account Two",
        accountType = "system.admin",
        email = Email("account02@test.com"),
        phone = Phone("255702020202"),
        password = SHA256.digest("02".toByteArray()).hex
    ).await()

    val accountX = accounts.create(
        UserAccount(
            name = "User Account X",
            type = "system.admin",
            scope = null
        )
    ).await()

    userService.addUserToAccount(accountX, user2.uid!!).await()

    for (i in 3..99) registerUser(i).await()

    for (i in 1..10) roles.create(
        UserRole(
            name = "Role ${i.show()}",
            permits = Authentication.accountTypes.random().permissionGroups.random().permissions.map { it.title }
        )
    ).await()
}