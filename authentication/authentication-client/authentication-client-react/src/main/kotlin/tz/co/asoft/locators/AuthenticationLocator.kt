@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationLocator(
    val service: AuthenticationServiceLocator,
    val repo: AuthenticationRepoLocator,
    val viewModel: AuthenticationViewModelLocator,
    val routes: AuthenticationRoutesLocator
)