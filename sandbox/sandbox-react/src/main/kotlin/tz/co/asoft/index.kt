package tz.co.asoft

import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import tz.co.asoft.locators.SandboxLocator
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }


fun setupAuthSandbox(): SandboxLocator {
    console.log("Setting up")
    setupTheme()
    setupLogging()
    val state = MutableStateFlow<SessionState>(SessionState.Unknown)
    val accountTypes = UserAccountType.all()
    val authorization = setupAuthorization(accountTypes)
    val authentication = setupAuthentication(state, accountTypes, authorization)
    return SandboxLocator(state, authorization, authentication)
}

fun main() = document.getElementById("root").setContent {
//    setupAuthSandbox()
//    val locator = setupAuthSandbox()
//    console.log(locator)
//    AuthSandbox(
//        signInPageUrl = "/imgs/sign-up.jpg",
//        locator
//    )
}