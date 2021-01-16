@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthenticationServiceLocator

class AuthenticationLocator(
    val service: AuthenticationServiceLocator,
    val repo: AuthenticationRepoLocator,
    val viewModel: AuthenticationViewModelLocator,
    val routes: AuthenticationRoutesLocator
)