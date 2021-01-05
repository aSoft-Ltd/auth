package tz.co.asoft

import react.RBuilder
import tz.co.asoft.website.HomePage
import tz.co.asoft.website.NotFound

fun RBuilder.AuthSandboxWebsite(signInPageImageUrl: String, moduleState: AuthModuleState) = Website(
    WebPage("/") { HomePage() },
    WebPage("/authentication/sign-in") { SignInPage(imageUrl = signInPageImageUrl, moduleState = moduleState) },
    WebPage("/404") { NotFound() }
)