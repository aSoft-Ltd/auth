package tz.co.asoft

import kotlinx.browser.document
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }

fun setupAuthSandbox() {
    setupTheme()
    setupLogging()
    setupInMemoryAuth(UsersLocalDao(kfg["package"] as String))
}

fun main() = document.getElementById("root").setContent {
    setupAuthSandbox()
    AuthSandbox(
        signInPageUrl = "/imgs/sign-up.jpg"
    )
}