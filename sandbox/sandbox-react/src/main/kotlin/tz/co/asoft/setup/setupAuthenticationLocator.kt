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
    val dao = AuthenticationDaoLocator(
        users = InMemoryUsersDao(),
        clientApps = InMemoryDao("client-app"),
        accounts = UserAccountsTestDao()
    )

    val service = UsersFrontendTestService(authorization.dao.claims, dao.accounts, usersLocalDao).apply { populate() }

    val repo = AuthenticationRepoLocator(service, dao)

    val viewModel = AuthenticationViewModelLocator(accountTypes, repo, authorization.repo, state)

    val routes = AuthenticationRoutesLocator(viewModel)

    return AuthenticationLocator(dao, service, repo, viewModel, routes)
}