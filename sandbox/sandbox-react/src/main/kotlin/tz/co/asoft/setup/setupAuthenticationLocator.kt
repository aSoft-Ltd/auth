@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow

fun setupAuthentication(
    namespace: String,
    state: MutableStateFlow<SessionState>,
    accountTypes: List<UserAccount.Type>,
    authorization: AuthorizationLocator
): AuthenticationLocator {
    val usersLocalDao = UsersLocalDao(namespace)

    val accountsDao = UserAccountsTestDao()

    val service = AuthenticationServiceLocator(
        accounts = accountsDao,
        users = UsersFrontendTestService(authorization.dao.claims, accountsDao, usersLocalDao).apply { populate() },
        clientApps = InMemoryDao("client-app")
    )

    val repo = AuthenticationRepoLocator(service)

    val viewModel = AuthenticationViewModelLocator(accountTypes, repo, authorization.repo, state)

    val routes = AuthenticationRoutesLocator(viewModel)

    return AuthenticationLocator(service, repo, viewModel, routes)
}