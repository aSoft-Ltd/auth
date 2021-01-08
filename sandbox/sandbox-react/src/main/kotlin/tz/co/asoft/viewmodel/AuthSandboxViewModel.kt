package tz.co.asoft.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tz.co.asoft.*

class AuthSandboxViewModel(
    state: MutableStateFlow<SessionState>,
    service: IUsersFrontendService
) : VModel<Any, SessionState>(SessionState.Unknown) {
    init {
        coroutineScope.launch {
            launch { service.authenticateLocallyOrLogout(state) }
            state.collect { ui.value = it }
        }
    }

    override fun CoroutineScope.execute(i: Any): Any = Unit
}