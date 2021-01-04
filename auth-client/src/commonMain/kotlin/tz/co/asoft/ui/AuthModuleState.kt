@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.ui.AuthModuleRepo
import tz.co.asoft.ui.AuthModuleViewModel

data class AuthModuleState(
    val accountTypes: List<UserAccount.Type>,
    val authenticationState: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Unknown),
    val service: AuthenticationService,
    val dao: AuthModuleDao
) {
    val repo = AuthModuleRepo(service.users, dao)
    val viewModel = AuthModuleViewModel(accountTypes, repo)
}