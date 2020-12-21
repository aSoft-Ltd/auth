@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.Align
import kotlinx.css.JustifyContent
import kotlinx.css.alignSelf
import react.RBuilder
import styled.css
import styled.styledDiv
import styled.styledH1

fun RBuilder.UserDetails(
    onChangeBasicInfo: (() -> Any)? = null,
    onChangePassword: (() -> Any)? = null,
    onSignOut: (() -> Any)? = null,
    user: User
) = ThemeConsumer { theme ->
    Grid(rows = "auto") {
        css {
            children { justifySelf = JustifyContent.center }
        }
        styledH1 {
            css { +theme.text.h2.clazz }
            +user.name
        }

        styledDiv {
            +user.emails.first()
        }

        styledDiv {
            +user.phones.first()
        }

        if (onChangeBasicInfo != null || onChangePassword != null) {
            Grid(cols = "auto auto auto") {
                css {
                    children {
                        alignSelf = Align.center
                        justifySelf = JustifyContent.center
                    }
                }
                ContainedButton("Logout", FaSignOutAlt) { onSignOut?.invoke() }
                ContainedButton("Info", FaPencilAlt) { onChangeBasicInfo?.invoke() }
                ContainedButton("Password", FaPencilAlt) { onChangePassword?.invoke() }
            }
        }
    }
}