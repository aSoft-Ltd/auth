package tz.co.asoft.website

import kotlinext.js.jsObject
import kotlinx.css.*
import react.RBuilder
import react.RProps
import react.dom.br
import react.functionalComponent
import react.router.dom.withRouter
import styled.css
import styled.styledDiv
import tz.co.asoft.*

fun RBuilder.HomePage() = Grid {
    css {
        width = 100.pct
        height = 100.vh
        justifyContent = JustifyContent.center
        alignContent = Align.center
    }
    Grid { theme ->
        css {
            children { justifySelf = JustifyContent.center }
        }
        styledDiv {
            css {
                textAlign = TextAlign.center
                alignSelf = Align.center
            }
            styledDiv {
                css { +theme.text.h1.clazz }
                +"We've got ourself a sandbox"
            }
            for (i in 1..2) br { }
            Buttons()
        }
    }
}

private val buttons = functionalComponent<RProps> { props ->
    Grid("1fr 1fr") {
        css { width = LinearDimension.auto }
        ContainedButton("Sign In") {
            props.history.push("/authentication/sign-in")
        }

        ContainedButton("Sign Up") {
            props.history.push("/authentication/sign-up")
        }
    }
}

private fun RBuilder.Buttons() = child(withRouter(buttons), jsObject()) {}