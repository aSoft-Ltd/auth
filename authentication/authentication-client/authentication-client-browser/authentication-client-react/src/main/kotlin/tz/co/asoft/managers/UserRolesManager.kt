@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.em
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.width
import react.RBuilder
import react.RProps
import styled.css
import styled.styledDiv
import tz.co.asoft.Authentication.systemPermits
import tz.co.asoft.Authentication.viewModels.rolesManager
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State

private class UserRolesManager : VComponent<RProps, Intent, State, RolesManagerViewModel>() {
    override val viewModel by lazy { rolesManager() }

    override fun componentDidMount() {
        super.componentDidMount()
        post(Intent.LoadRoles)
    }

    private fun UserRole.toTab(systemPermits: Set<Permit>) = Tab(name) {
        RoleManager(this@toTab, systemPermits) { post(Intent.DeleteRole(this@toTab)) }
    }

    private fun RBuilder.RoleTabs(data: List<UserRole>, systemPermits: Set<Permit>) = if (data.isEmpty()) {
        styledDiv { +"No Roles" }
    } else {
        Tabs(*data.map { it.toTab(systemPermits) }.toTypedArray())
    }

    private fun RBuilder.ShowRoleForm(systemPermits: Set<Permit>) = Surface(margin = 0.5.em) {
        UserRoleForm(
            role = null,
            systemPermits = systemPermits,
            onCancel = { post(Intent.LoadRoles) },
            onSubmit = { post(Intent.CreateRole(it)) }
        )
    }

    override fun RBuilder.render(ui: State) = styledDiv {
        css {
            width = 100.pct
            padding(0.5.em)
        }
        when (ui) {
            is State.Loading -> Loader(ui.msg)
            is State.RoleForm -> ShowRoleForm(ui.systemPermits)
            is State.Roles -> RoleTabs(ui.roles, ui.systemPermits)
            is State.Error -> Error(ui.msg)
        }
    }
}

fun RBuilder.RolesManager() = child(UserRolesManager::class) {}