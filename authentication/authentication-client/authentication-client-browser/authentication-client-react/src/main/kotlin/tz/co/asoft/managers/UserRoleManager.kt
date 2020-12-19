@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import react.RBuilder
import react.RProps
import styled.css
import styled.styledDiv
import tz.co.asoft.Authentication.viewModels.userRoleManager
import tz.co.asoft.RoleManager.Props
import tz.co.asoft.UserRoleManagerViewModel.Intent
import tz.co.asoft.UserRoleManagerViewModel.State

@JsExport
class RoleManager : VComponent<Props, Intent, State, UserRoleManagerViewModel>() {
    override val viewModel by lazy { userRoleManager() }

    class Props(
        val role: UserRole,
        val systemPermits: Set<Permit>,
        val onDelete: () -> Unit
    ) : RProps

    override fun componentDidMount() {
        super.componentDidMount()
        post(Intent.ViewRole(props.role))
    }

//    override fun componentWillReceiveProps(nextProps: Props) {
//        post(Intent.ViewRole(nextProps.role))
//    }

    private fun RBuilder.RolePermits(
        userPermits: List<Permit>,
        systemPermits: Set<Permit>
    ) = Grid(rows = "auto") {
        css { padding(0.5.em) }
        styledDiv {
            css {
                justifySelf = JustifyContent.end
                children { marginLeft = 1.em }
            }
            ContainedButton("Edit", FaPencilAlt) { post(Intent.RoleForm(props.role)) }
            ContainedButton("Delete", FaTrashAlt, props.onDelete)
        }
        PermitsView(
            userPermits, systemPermits,
            desktopHPadding = 20.pct
        )
    }

    override fun RBuilder.render(ui: State): Any = when (ui) {
        is State.Loading -> Loader(ui.msg)
        is State.RolePermits -> RolePermits(
            userPermits = ui.role.permits,
            systemPermits = props.systemPermits
        )
        is State.RoleForm -> UserRoleForm(
            role = ui.role,
            systemPermits = props.systemPermits,
            onCancel = { post(Intent.ViewRole(props.role)) },
            onSubmit = { post(Intent.EditRole(it)) }
        )
        is State.Error -> Error(ui.msg)
    }
}

fun RBuilder.RoleManager(
    role: UserRole,
    systemPermits: Set<Permit>,
    onDelete: () -> Unit
) = child(RoleManager::class.js, Props(role, systemPermits, onDelete)) {}