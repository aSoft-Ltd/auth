@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import kotlinx.html.InputType
import react.RBuilder
import styled.css
import styled.styledDiv

fun RBuilder.UserForm(
    onCancel: () -> Unit,
    onSubmit: (name: String, email: String, phone: String, role: UserRole) -> Unit,
    roles: List<UserRole>,
    user: User?
) = Form {
    css {
        width = 100.pct
        padding(1.em)
    }
    Grid { theme ->
        styledDiv {
            css {
                +theme.text.h2.clazz
                justifySelf = JustifyContent.center
            }
            +((if (user == null) "New" else "Edit") + " User")
        }

        TextInput(
            name = "name",
            label = "Name",
            value = user?.name,
            hint = "John Doe"
        )

        TextInput(
            name = "email",
            label = "Email",
            value = user?.emails?.firstOrNull(),
            type = InputType.email
        )

        TextInput(
            name = "phone",
            label = "Phone",
            value = user?.phones?.firstOrNull(),
            type = InputType.tel
        )

        DropDown(
            name = "userRole",
            options = listOf("Select Role") + roles.map { it.name }
        )

        Grid(cols = "auto auto") {
            OutlinedButton("Cancel", FaTimes, onClick = onCancel)
            ContainedButton("Submit", FaPaperPlane)
        }
    }
} onSubmit {
    val name by text()
    val email by text()
    val phone by text()
    val userRole by text()
    onSubmit(name, email, phone, roles.first { it.name == userRole })
}