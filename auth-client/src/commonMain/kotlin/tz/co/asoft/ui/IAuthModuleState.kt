@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.ui.AuthModuleRepo
import tz.co.asoft.ui.AuthModuleViewModel

interface IAuthModuleState {
    val accountTypes: List<UserAccount.Type>
    val authenticationState: MutableStateFlow<AuthenticationState>
    val service: AuthenticationService
    val dao: AuthModuleDao
    val repo get() = AuthModuleRepo(service.users, dao)
    val viewModel get() = AuthModuleViewModel(accountTypes, repo, authenticationState)
}