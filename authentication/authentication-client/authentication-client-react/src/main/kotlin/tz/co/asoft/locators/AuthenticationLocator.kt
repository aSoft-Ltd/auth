@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationLocator(
    val dao: AuthenticationDaoLocator,
    val service: IUsersFrontendService,
    val repo: AuthenticationRepoLocator,
    val viewModel: AuthenticationViewModelLocator,
    val routes: AuthenticationRoutesLocator
)