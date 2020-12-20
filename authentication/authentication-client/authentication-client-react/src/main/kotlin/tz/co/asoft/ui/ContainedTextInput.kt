@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import kotlinx.css.Color
import kotlinx.html.InputType
import kotlinx.html.id
import react.RBuilder
import react.RClass
import react.RProps
import react.RState
import react.dom.defaultValue
import styled.css
import styled.styledDiv
import styled.styledInput
import tz.co.asoft.ContainedTextInput.Props

@JsExport
class ContainedTextInput : Component<Props, RState>() {
    class Props(
        val name: String,
        val label: String,
        val hint: String,
        val value: String?,
        val type: InputType,
        val icon: RClass<IconProps>?,
        val isRequired: Boolean,
        val borderRadius: LinearDimension,
        val padding: LinearDimension,
        val data: Map<String, Any>?
    ) : RProps

    override fun RBuilder.render(): dynamic = ThemeConsumer { theme ->
        Grid(gap = 0.5.em) {
            styledDiv {
                +props.label
            }

            styledInput {
                css {
                    outline = Outline.none
                    color = Color.inherit
                    borderRadius = props.borderRadius
                    border = "solid 2px ${theme.primaryColor}"
                    backgroundColor = Color.transparent
                    padding(props.padding)
                }

                attrs {
                    id = props.name
                    name = props.name
                    placeholder = props.hint
                    props.value?.let { defaultValue = it }
                    type = if (props.type == InputType.tel) InputType.number else props.type
                    required = props.isRequired
                }

                props.data?.forEach { (key, value) ->
                    attrs["data-$key"] = value
                }
            }
        }
    }
}

fun RBuilder.ContainedTextInput(
    name: String,
    label: String = name,
    hint: String = "",
    type: InputType = InputType.text,
    icon: RClass<IconProps>? = null,
    value: String? = null,
    isRequired: Boolean = true,
    borderRadius: LinearDimension = 5.px,
    padding: LinearDimension = 0.5.em,
    data: Map<String, Any>? = null
) = child(ContainedTextInput::class.js, Props(name, label, hint, value, type, icon, isRequired, borderRadius, padding, data)) {}