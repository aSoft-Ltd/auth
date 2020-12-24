@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import kotlinx.css.properties.s
import kotlinx.css.properties.transform
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLDivElement
import react.*
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv
import tz.co.asoft.Accordion.Props

@JsExport
class Accordion private constructor() : Component<Props, RState>() {
    class Props(
        val header: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit,
        val content: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit
    ) : RProps

    private val titleUIID = UIID.getId("accordion-title")
    private val contentUIID = UIID.getId("accordion-content")

    private val title get() = titleUIID.get<HTMLDivElement>()
    private val content get() = contentUIID.get<HTMLDivElement>()

    private var height: LinearDimension? = null

    override fun componentDidMount() {
        height = content.offsetHeight.px
        setState { }
    }

    override fun componentWillUnmount() {
        contentUIID.release()
        super.componentWillUnmount()
    }

    override fun RBuilder.render(): dynamic = styledDiv {
        val header = props.header
        val body = props.content
        css {
            position = Position.relative
            padding(0.5.em)
            child(".title.opened") {
                before {
                    content = QuotedString("-")
                }
            }
            child(".content.opened") {
                height = this@Accordion.height ?: LinearDimension.auto
            }
        }
        ThemeConsumer { theme ->
            styledDiv {
                css {
                    position = Position.relative
                    cursor = Cursor.pointer
                    before {
                        content = QuotedString("+")
                        position = Position.absolute
                        top = 50.pct
                        right = 0.em
                        transform { translateY((-50).pct) }
                        fontSize = 1.5.em
                    }
                }
                attrs {
                    id = titleUIID.value
                    classes = setOf("title", "closed")
                    onClickFunction = {
                        title.classList.toggle("opened")
                        content.classList.toggle("opened")
                    }
                }
                header(theme)
            }

            styledDiv {
                css {
                    position = Position.relative
                    overflow = Overflow.hidden
                    transition(duration = 0.5.s)
                    height = if (this@Accordion.height == null) LinearDimension.auto else 0.px
                }
                attrs.classes = setOf("content")
                attrs.id = contentUIID.value
                body(theme)
            }
        }
    }
}

fun RBuilder.Accordion(
    header: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit,
    body: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit
) = child(Accordion::class.js, Props(header, body)) {}

fun RBuilder.Accordion(
    title: String,
    body: StyledDOMBuilder<DIV>.(ReactTheme) -> Unit
) = Accordion({ +title }, body)

