package tz.co.asoft.ui

import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledHr
import tz.co.asoft.FlexBox
import tz.co.asoft.Grid

fun RBuilder.TextBetweenLine(
    value: String,
    lineHeight: LinearDimension = 1.px,
    gap: LinearDimension = 0.5.em
) = Grid("1fr auto 1fr", gap = gap) {
    css { alignItems = Align.center }
    FlexBox { styledHr { css { width = 100.pct; height = lineHeight } } }
    FlexBox { +value }
    FlexBox { styledHr { css { width = 100.pct; height = lineHeight } } }
}