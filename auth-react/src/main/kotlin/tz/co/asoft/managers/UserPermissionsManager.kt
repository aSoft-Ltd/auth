@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.JustifyContent
import kotlinx.css.em
import kotlinx.css.pct
import kotlinx.css.width
import react.RBuilder
import react.RProps
import styled.css
import styled.styledDiv
import tz.co.asoft.Authentication.viewModels.userPermissionsManager
import tz.co.asoft.UserPermissionsManager.Props
import tz.co.asoft.UserPermissionsManagerViewModel.Intent
import tz.co.asoft.UserPermissionsManagerViewModel.State

private class UserPermissionsManager : VComponent<Props, Intent, State, UserPermissionsManagerViewModel>() {
    override val viewModel by lazy { userPermissionsManager() }

    class Props(val u: User) : RProps

    override fun componentDidMount() {
        super.componentDidMount()
        post(Intent.Init(props.u))
    }

    private fun RBuilder.UserRoleForm(ui: State.UserRoleForm) = Form {
        Grid(rows = "auto") {
            DropDown(
                name = "role",
                value = ui.userRole?.name,
                options = ui.roles.map { it.name }
            )
            Grid(cols = "auto auto") {
                OutlinedButton("Cancel", FaTimes) { post(Intent.Init(props.u)) }
                ContainedButton("Submit", FaPaperPlane)
            }
        }
    } onSubmit {
        val role by text()
        val r = ui.roles.first { it.name == role }
        post(Intent.UpdateUserWithRole(r, ui.u))
    }

    private fun RBuilder.UserPermissions(
        user: User,
        userPermits: Collection<String>,
        systemPermits: List<SystemPermissionGroup>
    ) = Grid(rows = "auto") { theme ->
        styledDiv {
            css {
                +theme.text.h2.clazz
                justifySelf = JustifyContent.center
            }
            +"User Permissions"
        }
        PermitsView(
            userPermits,
            systemPermits,
            desktopHPadding = 0.5.em
        )
        ContainedButton("Change Role") { post(Intent.UserRoleForm(user)) }
    }

    override fun RBuilder.render(ui: State): Any = when (ui) {
        is State.Loading -> AspectRationDiv {
            css { children { width = 100.pct } }
            ProgressBar()
        }
        is State.ShowPermissions -> UserPermissions(ui.u, ui.userPermits, ui.systemPermissions)
        State.ConcealPermissions -> styledDiv { }
        is State.UserRoleForm -> UserRoleForm(ui)
        is State.Error -> Error(ui.msg)
    }
}

fun RBuilder.UserPermissionsManager(user: User) = child(UserPermissionsManager::class.js, Props(user)) {}