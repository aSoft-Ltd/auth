package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import react.RBuilder
import react.RProps
import tz.co.asoft.AuthSandboxApp.Props
import tz.co.asoft.SessionState.*

@JsExport
class AuthSandboxApp : VComponent<Props, Any, SessionState, AuthSandboxViewModel>() {
    private val sessionState: MutableStateFlow<SessionState> = MutableStateFlow(Unknown)
    override val viewModel by lazy {
        AuthSandboxViewModel(sessionState)
    }

    class Props(
        val signInPageImageUrl: String
    ) : RProps

    override fun RBuilder.render(ui: SessionState) = ThemeProvider {
        val locator = viewModel.locator
        when (ui) {
            Unknown -> LoadingBox("Setting up workspace")
            LoggedOut -> AuthSandboxWebsite(
                signInPageImageUrl = props.signInPageImageUrl,
                viewModel = locator.authentication.viewModel
            )
            is LoggedIn -> PrincipleProvider(ui) {
                AuthSandboxWebapp(
                    state = ui,
                    moduleGroups = mapOf(
                        "Authorization" to locator.authorization.routes.menus("admin"),
                        "Authentication" to locator.authentication.routes.menus("admin")
                    ),
                    modules = locator.authorization.routes.modules("admin") +
                            locator.authentication.routes.modules("admin")
                )
            }
        }
    }
}

fun RBuilder.AuthSandbox(
    signInPageUrl: String
) = child(AuthSandboxApp::class.js, Props(signInPageUrl)) {}