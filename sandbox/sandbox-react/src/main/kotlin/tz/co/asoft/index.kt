package tz.co.asoft

import io.ktor.client.*
import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.locators.SandboxLocator
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }


fun setupAuthSandbox(): SandboxLocator {
    console.log("Setting up")
    val namespace: String by kfg
    setupTheme()
    setupLogging()
    val state = MutableStateFlow<SessionState>(SessionState.Unknown)
    val accountTypes = UserAccountType.all()
    val client = HttpClient()
    val authZDao = restAuthorizationDaoLocator(client)
    val authorization = setupAuthorization(accountTypes, authZDao)
    val authNService = ktorAuthenticationService(authorization, UsersLocalDao(namespace), client)
    val authentication = setupAuthentication(state, accountTypes, authorization, authNService)
    return SandboxLocator(state, authorization, authentication)
}

fun main() = document.getElementById("root").setContent {
    AuthSandbox(
        signInPageUrl = "/imgs/sign-up.jpg",
        locator = setupAuthSandbox()
    )
}