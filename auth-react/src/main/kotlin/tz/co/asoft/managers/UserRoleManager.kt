@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import react.RBuilder
import react.RProps
import react.functionalComponent
import react.useEffect
import styled.css
import styled.styledDiv
import tz.co.asoft.Authentication.viewModels.userRoleManager
import tz.co.asoft.UserRoleManagerViewModel.Intent
import tz.co.asoft.UserRoleManagerViewModel.State

private class Props(
    val role: UserRole,
    val systemPermits: Set<Permit>,
    val onDelete: () -> Unit
) : RProps

private val RoleManagerHook = functionalComponent<Props> { props->
    val vm = useViewModel { userRoleManager() }
    useEffect(listOf()) {
        vm.post(Intent.ViewRole(props.role))
    }
    val state by vm
    when (val ui: State = state) {
        is State.Loading -> Loader(ui.msg)
        is State.RolePermits -> RolePermits(
            userPermits = ui.role.permits,
            systemPermits = props.systemPermits,
            onEdit = ui.onEdit,
            onDelete = ui.onDelete
        )
        is State.RoleForm -> UserRoleForm(
            role = ui.role,
            systemPermits = props.systemPermits,
            onCancel = { vm.post(Intent.ViewRole(props.role)) },
            onSubmit = { vm.post(Intent.EditRole(it)) }
        )
        is State.Error -> Error(ui.msg)
    }
}

private fun RBuilder.RolePermits(
    userPermits: List<Permit>,
    systemPermits: Set<Permit>,
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
    systemPermits: Set<Permit>,
    onDelete: () -> Unit
) = child(RoleManagerHook, Props(role, systemPermits, onDelete)) {}