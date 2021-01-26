@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.GridTemplateColumns
import kotlinx.css.gridTemplateColumns
import kotlinx.css.padding
import kotlinx.css.pct
import react.RBuilder
import styled.css

fun RBuilder.UserInfo(user: User, locator: AuthenticationViewModelLocator) = Grid(rows = "auto") {
    css {
        onDesktop {
            padding(horizontal = 15.pct)
            gridTemplateColumns = GridTemplateColumns("1fr 1fr")
        }
        onMobile { gridTemplateColumns = GridTemplateColumns("1fr") }
    }
    UserProfilePicManager(user = user, locator = locator)
    Grid(rows = "auto") {
        UserDetailsManager(user = user, locator)
        UserPermissionsManager(user = user, locator)
    }
}