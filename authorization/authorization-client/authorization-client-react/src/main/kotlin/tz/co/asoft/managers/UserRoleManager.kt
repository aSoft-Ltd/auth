@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import react.RBuilder
import react.RProps
import react.functionalComponent
import react.useEffect
import styled.css
import styled.styledDiv
import tz.co.asoft.UserRoleManagerViewModel.Intent
import tz.co.asoft.UserRoleManagerViewModel.State
import tz.co.asoft.entities.UserRole

private class RoleManagerProps(
    val role: UserRole,
    val permissionGroups: List<SystemPermissionGroup>,
    val vmLocator: AuthorizationViewModelLocator,
    val onDelete: () -> Unit
) : RProps

private val RoleManagerHook = functionalComponent<RoleManagerProps> { props ->
    val vm = useViewModel { props.vmLocator.userRoleManager() }
    useEffect(listOf()) {
        vm.post(Intent.ViewRole(props.role))
    }
    val state by vm
    when (val ui: State = state) {
        is State.Loading -> Loader(ui.msg)
        is State.RolePermits -> RolePermits(
            userPermits = ui.role.permits,
            systemPermits = props.permissionGroups,
            onEdit = ui.onEdit,
            onDelete = ui.onDelete
        )
        is State.RoleForm -> UserRoleForm(
            role = ui.role,
            systemPermits = props.permissionGroups,
            onCancel = ui.onCancel,
            onSubmit = ui.onSubmit
        )
        is State.Error -> Error(ui.msg)
    }
}

private fun RBuilder.RolePermits(
    userPermits: Collection<String>,
    systemPermits: List<SystemPermissionGroup>,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) = Grid(rows = "auto") {
    css { padding(0.5.em) }
    styledDiv {
        css {
            justifySelf = JustifyContent.end
            children { marginLeft = 1.em }
        }
        ContainedButton("Edit", FaPencilAlt, onEdit)
        ContainedButton("Delete", FaTrashAlt, onDelete)
    }
    PermitsView(
        userPermits = userPermits,
        systemPermits = systemPermits,
        desktopHPadding = 20.pct
    )
}

fun RBuilder.RoleManager(
    role: UserRole,
    systemPermits: List<SystemPermissionGroup>,
    locator: AuthorizationViewModelLocator,
    onDelete: () -> Unit
) = child(RoleManagerHook, RoleManagerProps(role, systemPermits, locator, onDelete)) {}