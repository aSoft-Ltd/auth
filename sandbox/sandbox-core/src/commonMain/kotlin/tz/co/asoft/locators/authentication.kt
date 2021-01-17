@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun authenticationService(authZ: AuthorizationDaoLocator): AuthenticationServiceLocator {
    val clientApps = InMemoryDao<ClientApp>("client-app")
    val accountsDao = InMemoryDao<UserAccount>("user-account")
    return AuthenticationServiceLocator(
        UsersFrontendTestService(authZ.claims, accountsDao).apply { populate() },
        clientApps,
        accountsDao
    )
}