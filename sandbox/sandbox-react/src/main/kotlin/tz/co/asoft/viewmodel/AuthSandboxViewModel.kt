package tz.co.asoft.viewmodel

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tz.co.asoft.*

class AuthSandboxViewModel(
    private val usersService: IUsersFrontendService
) : VModel<Any, AuthenticationState>(AuthenticationState.Unknown) {
    init {
        launch {
            launch { usersService.authenticateLocallyOrLogout() }
            Authentication.state.collect { ui.value = it }
        }
    }

    override fun execute(i: Any): Any = Unit
}