@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthorizationDaoLocator
import tz.co.asoft.locators.AuthorizationRepoLocator

fun setupAuthorization(accountTypes: List<UserAccount.Type>): AuthorizationLocator {
    val dao = AuthorizationDaoLocator(
        claims = ClaimsTestDao(),
        roles = UserRolesTestDao()
    )

    val repo = AuthorizationRepoLocator(
        claims = Repo(dao.claims),
        roles = UserRolesTestRepo(dao.roles).apply { populate() }
    )

    val viewModel = AuthorizationViewModelLocator(
        accountTypes = accountTypes,
        repo = repo
    )

    val routes = AuthorizationRoutesLocator(viewModel)
    return AuthorizationLocator(dao, repo, viewModel, routes)
}