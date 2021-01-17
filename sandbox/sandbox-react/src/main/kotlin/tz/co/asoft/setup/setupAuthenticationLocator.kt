@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import io.ktor.client.*
import kotlinx.coroutines.flow.MutableStateFlow

fun inMemoryAuthenticationService(
    authorization: AuthorizationLocator,
    usersLocalDao: IUsersLocalDao
): AuthenticationFrontendServiceLocator {
    val accountsDao = UserAccountsTestDao()
    return AuthenticationFrontendServiceLocator(
        accounts = accountsDao,
        users = UsersFrontendTestService(authorization.dao.claims, accountsDao, usersLocalDao).apply { populate() },
        clientApps = InMemoryDao("client-app")
    )
}

fun ktorAuthenticationService(
    authorization: AuthorizationLocator,
    usersLocalDao: IUsersLocalDao,
    client: HttpClient
): AuthenticationFrontendServiceLocator {
    val options = RestfulOptions(url = "http://192.168.43.218:9020", "v1")
    val accountsDao = RestfulDao(
        options = options,
        root = "authentication",
        subRoot = "user-accounts",
        token = null,
        client = client,
        serializer = UserAccount.serializer()
    )

    return AuthenticationFrontendServiceLocator(
        accounts = accountsDao,
        users = KtorUsersFrontendService(
            localDao = usersLocalDao,
            claimsDao = authorization.dao.claims,
            accountsDao = accountsDao,
            options = options,
            token = null,
            client = client
        ),
        clientApps = InMemoryDao("client-app")
    )
}

fun setupAuthentication(
    state: MutableStateFlow<SessionState>,
    accountTypes: List<UserAccount.Type>,
    authorization: AuthorizationLocator,
    service: AuthenticationFrontendServiceLocator
): AuthenticationLocator {
    val repo = AuthenticationRepoLocator(service)

    val viewModel = AuthenticationViewModelLocator(accountTypes, repo, authorization.repo, state)

    val routes = AuthenticationRoutesLocator(viewModel)

    return AuthenticationLocator(service, repo, viewModel, routes)
}