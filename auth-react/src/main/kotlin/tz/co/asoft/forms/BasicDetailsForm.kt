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

fun RBuilder.BasicInfoForm(
    onCancel: () -> Unit,
    onSubmit: (name: String, email: String, phone: String) -> Unit,
    user: User
) = Form {
    css { width = 100.pct }
    Grid(rows = "auto") { theme ->
        styledH1 {
            css {
                textAlign = TextAlign.center
                +theme.text.h2.clazz
            }
            +"Change Basic Info"
        }

        TextInput(
            name = "name",
            value = user.name,
            label = "Full Name"
        )

        TextInput(
            name = "email",
            label = "Email",
            value = user.emails.firstOrNull(),
            type = InputType.email
        )

        TextInput(
            name = "phone",
            label = "Phone",
            value = user.phones.firstOrNull(),
            type = InputType.tel
        )

        Grid(cols = "1fr 1fr") {
            OutlinedButton("Cancel", FaTimes, onClick = onCancel)
            ContainedButton("Submit", FaPaperPlane)
        }
    }
} onSubmit {
    val name by text()
    val email by text()
    val phone by text()
    onSubmit(name, email, phone)
}