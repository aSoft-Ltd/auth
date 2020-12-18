package tz.co.asoft

import kotlinx.css.height
import kotlinx.css.vh
import react.RBuilder
import react.RProps
import react.dom.span
import styled.css
import tz.co.asoft.AuthSandboxApp.Props
import tz.co.asoft.AuthenticationState.*
import tz.co.asoft.viewmodel.AuthSandboxViewModel

@JsExport
class AuthSandboxApp : VComponent<Props, Any, AuthenticationState, AuthSandboxViewModel>() {
    override val viewModel by lazy { AuthSandboxViewModel(Authentication.service.users) }

    class Props(
        val signInPageUrl: String
    ) : RProps

    private fun RBuilder.Webapp(ui: LoggedIn) = Grid {
        span { +"Logged in as : ${ui.user.name} with account ${ui.account.name}" }
    }

    override fun RBuilder.render(ui: AuthenticationState) = ThemeProvider {
        console.log(ui)
        when (ui) {
            Unknown -> Grid {
                css { centerContent(); height = 100.vh }
                Loader("Setting up workspace")
            }
            LoggedOut -> AuthSandboxWebsite(signInPageImageUrl = props.signInPageUrl)
            is LoggedIn -> Webapp(ui)
        }
    }
}

fun RBuilder.AuthSandbox(
    signInPageUrl: String
) = child(AuthSandboxApp::class.js, Props(signInPageUrl)) {}