package tz.co.asoft

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }

fun setupAuthSandbox() {
    console.log("Setting up")
    setupTheme()
    setupLogging()
    Authentication.accountTypes = UserAccountType.all()
    setupInMemoryAuth(UsersLocalDao(kfg["package"] as String)).finally {
        console.log("Finished setting up")
    }
}

fun main() = document.getElementById("root").setContent {
    setupAuthSandbox()
    AuthSandbox(
        signInPageUrl = "/imgs/sign-up.jpg"
    )
}