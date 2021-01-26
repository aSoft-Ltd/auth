@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import io.ktor.client.*

fun inMemoryAuthorizationDaoLocator() = AuthorizationDaoLocator(
    claims = ClaimsTestDao(),
    roles = UserRolesTestDao().apply { populate() }
)

fun restAuthorizationDaoLocator(token: String?, client: HttpClient): AuthorizationDaoLocator {
    val options = KtorDaoOptions(url = "http://192.168.43.218:9010", "v1")
    return AuthorizationDaoLocator(
        claims = KtorRestDao(
            options = options,
            serializer = Claim.serializer(),
            root = "authorization",
            subRoot = "claims",
            client = client,
            token = token
        ),
        roles = KtorRestDao(
            options = options,
            serializer = UserRole.serializer(),
            root = "authorization",
            subRoot = "user-roles",
            client = client,
            token = token
        )
    )
}


fun setupAuthorization(accountTypes: List<UserAccount.Type>, dao: AuthorizationDaoLocator): AuthorizationLocator {
    val repo = AuthorizationRepoLocator(
        claims = Repo(dao.claims),
        roles = Repo(dao.roles)
    )

    val viewModel = AuthorizationViewModelLocator(
        accountTypes = accountTypes,
        repo = repo
    )

    val routes = AuthorizationRoutesLocator(viewModel)
    return AuthorizationLocator(dao, repo, viewModel, routes)
}