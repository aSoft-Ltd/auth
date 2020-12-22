@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.em
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.width
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv
import tz.co.asoft.Authentication.viewModels.rolesManager
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

private fun UserRole.toTab(
    systemPermits: Set<Permit>,
    onDelete: () -> Unit
) = Tab(name) {
    RoleManager(this@toTab, systemPermits, onDelete)
}

private fun RBuilder.RoleTabs(
    data: List<UserRole>,
    systemPermits: Set<Permit>,
    onDelete: (UserRole) -> Unit
) = if (data.isEmpty()) {
    styledDiv { +"No Roles" }
} else {
    Tabs(*data.map { it.toTab(systemPermits) { onDelete(it) } }.toTypedArray())
}

private fun RBuilder.ShowRoleForm(
    systemPermits: Set<Permit>,
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
                systemPermits = ui.systemPermits,
                onCancel = { vm.post(Intent.LoadRoles) },
                onSubmit = { vm.post(Intent.CreateRole(it)) }
            )
            is State.Roles -> RoleTabs(
                data = ui.roles,
                systemPermits = ui.systemPermits,
                onDelete = { vm.post(Intent.DeleteRole(it)) }
            )
            is State.Error -> Error(ui.msg)
        }
    }
}

fun RBuilder.RolesManager() = child(UserRolesManagerHook)