package tz.co.asoft.locators

import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.AuthenticationLocator
import tz.co.asoft.AuthorizationLocator
import tz.co.asoft.SessionState

class SandboxLocator(
    val state: MutableStateFlow<SessionState>,
    val authorization: AuthorizationLocator,
    val authentication: AuthenticationLocator
)