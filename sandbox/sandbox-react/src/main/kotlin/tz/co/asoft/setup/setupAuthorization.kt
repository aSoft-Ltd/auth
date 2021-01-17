@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import io.ktor.client.*

fun inMemoryAuthorizationDaoLocator() = AuthorizationDaoLocator(
    claims = ClaimsTestDao(),
    roles = UserRolesTestDao().apply { populate() }
)

fun restAuthorizationDaoLocator(client: HttpClient): AuthorizationDaoLocator {
    val options = RestfulOptions(url = "http://192.168.43.218:9010", "v1")
    return AuthorizationDaoLocator(
        claims = RestfulDao(
            options = options,
            serializer = Claim.serializer(),
            root = "authorization",
            subRoot = "claims",
            client = client,
            token = null
        ),
        roles = RestfulDao(
            options = options,
            serializer = UserRole.serializer(),
            root = "authorization",
            subRoot = "user-roles",
            client = client,
            token = null
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