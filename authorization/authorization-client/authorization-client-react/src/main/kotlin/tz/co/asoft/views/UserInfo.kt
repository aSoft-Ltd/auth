@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.GridTemplateColumns
import kotlinx.css.gridTemplateColumns
import kotlinx.css.padding
import kotlinx.css.pct
import react.RBuilder
import styled.css

fun RBuilder.UserInfo(user: User, moduleState: AuthModuleState) = Grid(rows = "auto") {
    css {
        onDesktop {
            padding(horizontal = 15.pct)
            gridTemplateColumns = GridTemplateColumns("1fr 1fr")
        }
        onMobile { gridTemplateColumns = GridTemplateColumns("1fr") }
    }
    UserProfilePicManager(user = user, moduleState = moduleState)
    Grid(rows = "auto") {
        UserDetailsManager(user = user, moduleState)
        UserPermissionsManager(user = user, moduleState)
    }
}