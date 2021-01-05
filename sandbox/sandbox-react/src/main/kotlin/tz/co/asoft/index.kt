package tz.co.asoft

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }

fun setupAuthSandbox(): AuthModuleState {
    console.log("Setting up")
    setupTheme()
    setupLogging()
    val dao = AuthModuleDao(
        users = InMemoryUsersDao(),
        clientApps = InMemoryDao("client-app"),
        accounts = InMemoryDao("user-account"),
        claims = InMemoryDao("claim"),
        roles = InMemoryDao("role")
    )
    val moduleState = AuthModuleState(
        accountTypes = UserAccountType.all(),
        dao = dao,
        service = AuthenticationService(
            InMemoryUserFrontEndService(
                claimsDao = dao.claims,
                accountsDao = dao.accounts,
                localDao = InMemoryUsersLocalDao()
            )
        ),
    )
    return moduleState
}

fun main() = document.getElementById("root").setContent {
    val state = setupAuthSandbox()
    console.log(state)
    AuthSandbox(
        signInPageUrl = "/imgs/sign-up.jpg",
        state
    )
}