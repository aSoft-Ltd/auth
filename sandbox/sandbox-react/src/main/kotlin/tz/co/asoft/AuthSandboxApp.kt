package tz.co.asoft

import kotlinx.css.height
import kotlinx.css.vh
import react.RBuilder
import react.RProps
import styled.css
import tz.co.asoft.AuthSandboxApp.Props
import tz.co.asoft.SessionState.*
import tz.co.asoft.viewmodel.AuthSandboxViewModel

@JsExport
class AuthSandboxApp : VComponent<Props, Any, SessionState, AuthSandboxViewModel>() {
    override val viewModel by lazy { AuthSandboxViewModel(props.moduleState) }

    class Props(
        val signInPageImageUrl: String,
        val moduleState: AuthModuleState
    ) : RProps

    override fun RBuilder.render(ui: SessionState) = ThemeProvider {
        when (ui) {
            Unknown -> Grid {
                css { centerContent(); height = 100.vh }
                Loader("Setting up workspace")
            }
            LoggedOut -> AuthSandboxWebsite(
                signInPageImageUrl = props.signInPageImageUrl,
                moduleState = props.moduleState
            )
            is LoggedIn -> PrincipleProvider(ui) {
                AuthSandboxWebapp(
                    state = ui,
                    moduleGroups = mapOf(
                        "Authentication" to props.moduleState.menus("admin")
                    ),
                    modules = props.moduleState.modules(ui, "admin")
                )
            }
        }
    }
}

fun RBuilder.AuthSandbox(
    signInPageUrl: String,
    moduleState: AuthModuleState
) = child(AuthSandboxApp::class.js, Props(signInPageUrl, moduleState)) {}