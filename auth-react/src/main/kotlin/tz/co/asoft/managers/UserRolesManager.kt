@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import kotlinx.html.InputType
import react.RBuilder
import react.RProps
import react.child
import react.dom.li
import react.dom.ul
import react.functionalComponent
import styled.*
import tz.co.asoft.Authentication.viewModels.rolesManager
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

private fun UserRole.toTab(
    systemPermits: List<SystemPermissionGroup>,
    onDelete: () -> Unit
) = Tab(name) { RoleManager(this@toTab, systemPermits, onDelete) }

private fun RBuilder.RoleCard(role: UserRole) = Accordion(role.name) {
    ul {
        role.permits.forEach {
            li {
                styledInput(type = InputType.checkBox) {}
                styledP { +it.toString() }
            }
        }
    }
}

private fun RBuilder.RolesCard(
    data: List<UserRole>,
    systemPermits: List<SystemPermissionGroup>,
    onDelete: (UserRole) -> Unit
) = Grid {
    css {
        onDesktop { padding(horizontal = 15.pct) }
        onMobile { padding(0.5.em) }
    }
    Surface {
        if (data.isEmpty()) {
            +"No roles to display"
        } else {
            Grid {
                for (role in data) RoleCard(role)
            }
        }
    }
}

private fun RBuilder.RoleTabs(
    data: List<UserRole>,
    systemPermits: List<SystemPermissionGroup>,
    onDelete: (UserRole) -> Unit
) = if (data.isEmpty()) {
    styledDiv { +"No Roles" }
} else {
    Tabs(*data.map { it.toTab(systemPermits) { onDelete(it) } }.toTypedArray())
}

private fun RBuilder.ShowRoleForm(
    systemPermits: List<SystemPermissionGroup>,
    onCancel: () -> Unit,
    onSubmit: (UserRole) -> Unit
) = Surface(margin = 0.5.em) {
    UserRoleForm(
        role = null,
        systemPermits = systemPermits,
        onCancel = onCancel,// { post(Intent.LoadRoles) },
        onSubmit = onSubmit //{ post(Intent.CreateRole(it)) }
    )
}

private val UserRolesManagerHook = functionalComponent<RProps> {
    val vm = useViewModel { rolesManager() }
    val state by vm
    styledDiv {
        css {
            width = 100.pct
            padding(0.5.em)
        }
        when (val ui = state) {
            is State.Loading -> Loader(text = ui.msg)
            is State.RoleForm -> ShowRoleForm(
                systemPermits = ui.permissionGroups,
                onCancel = { vm.post(Intent.LoadRoles) },
                onSubmit = { vm.post(Intent.CreateRole(it)) }
            )
            is State.Roles -> RolesCard(
                data = ui.roles,
                systemPermits = ui.permissionGroups,
                onDelete = { vm.post(Intent.DeleteRole(it)) }
            )
            is State.Error -> Error(ui.msg)
        }
    }
}

fun RBuilder.RolesManager() = child(UserRolesManagerHook)