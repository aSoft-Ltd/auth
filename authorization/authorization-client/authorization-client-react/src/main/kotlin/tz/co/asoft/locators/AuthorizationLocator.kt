@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthorizationLocator(
    val dao: AuthorizationDaoLocator,
    val repo: AuthorizationRepoLocator,
    val viewModel: AuthorizationViewModelLocator,
    val routes: AuthorizationRoutesLocator
)