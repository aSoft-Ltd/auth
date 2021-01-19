@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tz.co.asoft.*
import tz.co.asoft.locators.SandboxLocator

class AuthSandboxViewModel(
    private val state: MutableStateFlow<SessionState>
) : VModel<Any, SessionState>(SessionState.Unknown) {
    init {
        val usersService = locator.authentication.service.users
        coroutineScope.launch {
            launch { usersService.authenticateLocallyOrLogout(state) }
            state.collect { ui.value = it }
        }
    }

    val locator
        get() = when (val session = ui.value) {
            is SessionState.LoggedIn -> SandboxLocator(state, session.token)
            else -> SandboxLocator(state, null)
        }

    override fun CoroutineScope.execute(i: Any): Any = Unit
}