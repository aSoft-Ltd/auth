@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.dom.div
import react.dom.span
import react.router.dom.routeLink
import react.router.dom.withRouter
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv
import tz.co.asoft.LoginFormViewModel.Intent
import tz.co.asoft.LoginFormViewModel.State
import tz.co.asoft.SignInPage.Props

@JsExport
class SignInPage : VComponent<Props, Intent, State, LoginFormViewModel>() {
    override val viewModel by lazy { props.moduleState.viewModel.loginForm() }

    class Props(
        val imageUrl: String,
        val includeSignUpLink: Boolean,
        val moduleState: AuthModuleState,
        val overlay: (StyledDOMBuilder<DIV>.() -> Unit)?
    ) : RProps

    private fun RBuilder.LeftSide(url: String) = styledDiv {
        css {
            height = 100.pct
            alignContent = Align.center
            backgroundImage = kotlinx.css.Image("url('$url')")
            backgroundSize = "cover"
            backgroundPosition = "center"
            onDesktop {
                display = Display.grid
            }
            onMobile {
                display = Display.none
            }
        }
        props.overlay?.let { it() }
    }

    private fun RBuilder.LoginForm(email: String?) = Form { theme ->
        css {
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns("1fr")
            alignItems = Align.center
            gap = Gap("1em")
            child("button") {
                width = 100.pct
                padding(1.em)
            }
        }

        FlexBox {
            css {
                +theme.text.h1.clazz
            }
            +"Secure Login"
        }

        ContainedTextInput(
            name = "email",
            label = "Email",
            value = email,
            type = InputType.email,
            padding = 1.1.em,
            borderRadius = 0.5.em
        )

        ContainedTextInput(
            name = "password",
            label = "Password",
            type = InputType.password,
            padding = 1.1.em,
            borderRadius = 0.5.em
        )

        ContainedButton("Login")
    } onSubmit {
        val email by text()
        val password by text()
        post(Intent.SignIn(email, password.toByteArray()))
    }

    private fun RBuilder.RightSideWrapper(builder: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit) = Grid { theme ->
        css {
            display = Display.grid
            backgroundColor = theme.surfaceColor
            color = theme.onSurfaceColor
            alignContent = Align.center
            padding(15.pct)
        }
        builder(theme)
    }

    private fun RBuilder.ShowForm(email: String?) = RightSideWrapper {
        LoginForm(email)
        if (props.includeSignUpLink) {
            TextBetweenLine("OR")
            div {
                span { +"Don't have an account? " }
                routeLink(to = "/authentication/sign-up") { +"Sign Up" }
            }
        }
    }

    private fun RBuilder.ShowAccountSelection(u: User) = RightSideWrapper { theme ->
        css {
            gridTemplateRows = GridTemplateRows("auto auto")
        }
        FlexBox {
            css {
                +theme.text.h2.clazz
                justifyContent = JustifyContent.center
            }
            +"Select Account"
        }

        Grid(u.accounts.joinToString(" ") { "1fr" }) {
            for (account in u.accounts) UserAccount(
                account = account,
                onClicked = { post(Intent.AuthenticateAccount(account, u)) }
            )
        }
    }

    private fun RBuilder.UserAccount(account: UserAccount, onClicked: () -> Unit) = Grid(gap = 0.5.em) { theme ->
        css {
            border = "solid 1px transparent"
            cursor = Cursor.pointer
            transition(duration = 0.2.s)
            hover { border = "solid 1px ${theme.onSurfaceColor}" }
            padding(1.em)
            children {
                display = Display.grid
                alignItems = Align.center
                justifyContent = JustifyContent.center
            }
        }
        attrs.onClickFunction = { onClicked() }
        styledDiv {
            css { +theme.text.h1.clazz }
            FaUser {}
        }
        styledDiv {
            +account.name
        }
    }

    override fun RBuilder.render(ui: State) = Grid(gap = 0.px) {
        css {
            height = 100.vh
            onMobile {
                gridTemplateColumns = GridTemplateColumns("1fr")
            }
            onDesktop {
                gridTemplateColumns = GridTemplateColumns("1fr 1fr")
            }
        }
        LeftSide(props.imageUrl)
        when (ui) {
            is State.Loading -> Loader(ui.msg)
            is State.ShowForm -> ShowForm(ui.email)
            is State.AccountSelection -> ShowAccountSelection(ui.user)
            is State.Error -> Error("${ui.cause}")
            is State.Success -> Success("Success")
        }.let { }
    }
}

fun RBuilder.SignInPage(
    imageUrl: String,
    includeSignUpLink: Boolean = true,
    moduleState: AuthModuleState,
    overlay: (StyledDOMBuilder<DIV>.() -> Unit)? = null
) = child(withRouter(SignInPage::class), Props(imageUrl, includeSignUpLink, moduleState, overlay)) {}