@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthenticationServiceLocator

fun authenticationService(authZ: AuthorizationDaoLocator): AuthenticationServiceLocator {
    val clientApps = InMemoryDao<ClientApp>("client-app")
    val accountsDao = InMemoryDao<UserAccount>("user-account")
    return AuthenticationServiceLocator(
        UsersFrontendTestService(authZ.claims, accountsDao),
        clientApps,
        accountsDao
    )
}