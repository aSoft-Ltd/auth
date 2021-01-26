package tz.co.asoft.locators

import io.ktor.client.*
import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.*
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

class SandboxLocator(
    val state: MutableStateFlow<SessionState>,
    val authorization: AuthorizationLocator,
    val authentication: AuthenticationLocator
)

fun SandboxLocator(state: MutableStateFlow<SessionState>, token: String?): SandboxLocator {
    console.log("Setting up")
    val namespace: String by kfg
    setupTheme()
    setupLogging()
    val accountTypes = UserAccountType.all()
    val client = HttpClient()
    val authZDao = restAuthorizationDaoLocator(token, client)
    val authorization = setupAuthorization(accountTypes, authZDao)
    val authNService = ktorAuthenticationService(authorization, TokenStorage(namespace), token, client)
    val authentication = setupAuthentication(state, accountTypes, authorization, authNService)
    return SandboxLocator(state, authorization, authentication)
}