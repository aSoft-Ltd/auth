@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.router.dom.withRouter
import styled.css
import styled.styledDiv
import tz.co.asoft.UsersContainer.Props
import tz.co.asoft.UsersManagerViewModel.Intent

@JsExport
class UsersContainer : RComponent<Props, RState>() {
    class Props(
        val drawerController: MutableStateFlow<DrawerState>?
    ) : RProps

    private val inputCss: RuleSet = {
        onMobile {
            width = 90.pct
            minWidth = LinearDimension.auto
            margin(horizontal = 5.pct)
        }
    }

    override fun RBuilder.render(): dynamic = MainDrawerControllerConsumer { mainDrawerController ->
        styledDiv {
            css { width = 100.pct }
            NavigationAppBar(
                drawerController = props.drawerController ?: mainDrawerController,
                left = { +"Users" },
                middle = {
                    SearchInput(hint = "Search Users", css = inputCss) { key ->
                        val predicate = { user: User ->
                            Json.encodeToString(User.serializer(), user).contains(key, ignoreCase = true) && !user.deleted
                        }
                        UsersManagerViewModel.post(Intent.ViewUsers(predicate))
                    }
                },
                right = {
                    TextButton("New User", FaPlus) { UsersManagerViewModel.post(Intent.ViewForm) }
                }
            )
            UsersManager()
        }
    }
}

/**
 * Contains all the UI for Roles
 * @param drawerController the drawer that this RolesContainer controls
 *      if null is provided, the [MainDrawerController is used
 */
fun RBuilder.UsersContainer(
    drawerController: MutableStateFlow<DrawerState>? = null
) = child(UsersContainer::class.js, Props(drawerController)) {}