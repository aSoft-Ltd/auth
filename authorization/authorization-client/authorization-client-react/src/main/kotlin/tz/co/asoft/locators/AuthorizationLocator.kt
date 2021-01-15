@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.locators.AuthorizationRepoLocator

class AuthorizationLocator(
    val dao: AuthorizationDaoLocator,
    val repo: AuthorizationRepoLocator,
    val viewModel: AuthorizationViewModelLocator,
    val routes: AuthorizationRoutesLocator
)