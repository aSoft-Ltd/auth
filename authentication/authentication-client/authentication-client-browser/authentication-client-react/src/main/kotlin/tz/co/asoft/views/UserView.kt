@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.rem
import react.RBuilder
import react.router.dom.routeLink
import styled.css
import styled.styledDiv
import styled.styledP
import kotlin.Suppress

fun RBuilder.UserView(user: User) = Grid(cols = "1fr 3fr") { theme ->
    ProfilePic(
        name = user.name,
        src = user.photoUrl,
        textFontSize = 1.5.rem
    )
    Grid(rows = "auto auto auto") {
        styledDiv {
            css { +theme.text.h4.clazz }
            routeLink(to = "/profile/${user.uid}") { +user.name }
        }
        Grid(cols = "auto 1fr") {
            FaEnvelope {}
            styledP { +user.emails.first() }
        }
        Grid(cols = "auto 1fr") {
            FaPhone {}
            styledP { +user.phones.first() }
        }
    }
}