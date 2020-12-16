@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.TextAlign
import kotlinx.css.pct
import kotlinx.css.textAlign
import kotlinx.css.width
import kotlinx.html.InputType
import react.RBuilder
import styled.css
import styled.styledH1
import tz.co.asoft.*

fun RBuilder.PasswordForm(
    onCancel: () -> Unit,
    onSubmit: (oldPass: String, newPass: String) -> Unit
) = Form {
    css { width = 100.pct }
    Grid(rows = "auto") { theme ->
        styledH1 {
            css {
                textAlign = TextAlign.center
                +theme.text.h2.clazz
            }
            +"Change Credentials"
        }

        TextInput(
            name = "oldPassword",
            label = "Old Password",
            type = InputType.password
        )

        TextInput(
            name = "newPassword",
            label = "New Password",
            type = InputType.password
        )

        TextInput(
            name = "confPassword",
            label = "Confirm New Password",
            type = InputType.password
        )

        Grid(cols = "1fr 1fr") {
            OutlinedButton("Cancel", FaTimes, onClick = onCancel)
            ContainedButton("Submit", FaPaperPlane)
        }
    }
} onSubmit {
    val oldPassword by text()
    val newPassword by text()
    val confPassword by text()

    if (newPassword != confPassword) {
        error(newPassword, "Doesn't Match")
        error(confPassword, "Doesn't Match")
        return@onSubmit
    }
    onSubmit(oldPassword, newPassword)
}