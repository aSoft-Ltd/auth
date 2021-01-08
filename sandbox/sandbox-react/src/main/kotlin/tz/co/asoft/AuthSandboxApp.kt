package tz.co.asoft

import kotlinx.css.height
import kotlinx.css.vh
import react.RBuilder
import react.RProps
import styled.css
import tz.co.asoft.AuthSandboxApp.Props
import tz.co.asoft.SessionState.*
import tz.co.asoft.locators.SandboxLocator
import tz.co.asoft.viewmodel.AuthSandboxViewModel

@JsExport
class AuthSandboxApp : VComponent<Props, Any, SessionState, AuthSandboxViewModel>() {
    override val viewModel by lazy {
        AuthSandboxViewModel(props.locator.state, props.locator.authentication.service)
    }

    class Props(
        val signInPageImageUrl: String,
        val locator: SandboxLocator
    ) : RProps

    override fun RBuilder.render(ui: SessionState) = ThemeProvider {
        when (ui) {
            Unknown -> LoadingBox("Setting up workspace")
            LoggedOut -> AuthSandboxWebsite(
                signInPageImageUrl = props.signInPageImageUrl,
                viewModel = props.locator.authentication.viewModel
            )
            is LoggedIn -> PrincipleProvider(ui) {
                AuthSandboxWebapp(
                    state = ui,
                    moduleGroups = mapOf(
                        "Authorization" to props.locator.authorization.routes.menus("admin"),
                        "Authentication" to props.locator.authentication.routes.menus("admin")
                    ),
                    modules = props.locator.authorization.routes.modules("admin") +
                            props.locator.authentication.routes.modules("admin")
                )
            }
        }
    }
}

fun RBuilder.AuthSandbox(
    signInPageUrl: String,
    locator: SandboxLocator
) = child(AuthSandboxApp::class.js, Props(signInPageUrl, locator)) {}