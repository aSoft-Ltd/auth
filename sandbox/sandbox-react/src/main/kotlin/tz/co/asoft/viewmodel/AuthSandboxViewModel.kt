package tz.co.asoft.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tz.co.asoft.*

class AuthSandboxViewModel(
    private val moduleState: AuthModuleState
) : VModel<Any, SessionState>(SessionState.Unknown) {
    init {
        coroutineScope.launch {
            val state = moduleState.sessionState
            launch { moduleState.service.users.authenticateLocallyOrLogout(state) }
            state.collect { ui.value = it }
        }
    }

    override fun CoroutineScope.execute(i: Any): Any = Unit
}