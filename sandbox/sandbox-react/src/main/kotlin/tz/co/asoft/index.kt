package tz.co.asoft

import kotlinx.browser.document
import tz.co.asoft.locators.SandboxLocator

val kfg by lazy { konfig() }

fun main() = document.getElementById("root").setContent {
    AuthSandbox(signInPageUrl = "/imgs/sign-up.jpg")
}