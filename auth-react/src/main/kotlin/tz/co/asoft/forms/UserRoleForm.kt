@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledH1

fun RBuilder.UserRoleForm(
    role: UserRole?,
    systemPermits: List<SystemPermissionGroup>,
    onCancel: () -> Unit,
    onSubmit: (UserRole) -> Unit
) = Form {
    Grid(rows = "auto") {
        css {
            onDesktop {
                padding(vertical = 2.em, horizontal = 30.pct)
            }
            onMobile {
                padding(1.em)
            }
        }
        styledH1 {
            css { textAlign = TextAlign.center }
            +"${if (role == null) "New" else "Edit"} Role"
        }

        TextInput(
            name = "name",
            value = role?.name,
            label = "Role Name"
        )

        for (group in systemPermits) Accordion(group.name) {
            for (permit in group.permissions) Switch(
                name = "permits",
                checked = role?.permits?.contains(permit.name) == true,
                label = permit.name,
                value = permit.name
            )
        }

        Grid(cols = "1fr 1fr") {
            OutlinedButton("Cancel", FaTimes, onCancel)
            ContainedButton("Submit", FaPaperPlane)
        }
    }
} onSubmit {
    val name by text()
    val permits by multi()
    onSubmit(
        UserRole(
            uid = role?.uid,
            name = name,
            permits = permits,
            deleted = role?.deleted ?: false
        )
    )
}