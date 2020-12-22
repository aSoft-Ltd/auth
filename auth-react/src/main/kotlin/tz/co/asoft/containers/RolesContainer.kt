@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.css.pct
import kotlinx.css.width
import react.RBuilder
import styled.css
import styled.styledDiv
import tz.co.asoft.RolesManagerViewModel.Intent

/**
 * Contains all the UI for Roles
 * @param drawerController the drawer that this RolesContainer controls
 *      if null is provided, the [MainDrawerController is used
 */
fun RBuilder.RolesContainer(
    state: AuthenticationState.LoggedIn,
    drawerController: MutableStateFlow<DrawerState>? = null
) = MainDrawerControllerConsumer { mainDrawerController ->
    styledDiv {
        css { width = 100.pct }
        RolesContainerAppBar(state, drawerController)
        RolesManager()
    }
}

private fun RBuilder.RolesContainerAppBar(
    state: AuthenticationState.LoggedIn,
    drawerController: MutableStateFlow<DrawerState>?
) = MainDrawerControllerConsumer { mainDrawerController ->
    NavigationAppBar(
        drawerController = drawerController ?: mainDrawerController,
        left = { +"Roles" },
        right = {
            if (state.has(claim = "roles:create")) {
                TextButton("New Role", FaPlus) { RolesManagerViewModel.post(Intent.NewRoleForm) }
            }
        }
    )
}