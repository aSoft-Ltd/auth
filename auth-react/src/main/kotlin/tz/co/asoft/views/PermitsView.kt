@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.LinearDimension
import kotlinx.css.padding
import react.RBuilder
import styled.css

internal fun RBuilder.PermitsView(
    userPermits: List<Permit>,
    systemPermits: Set<Permit>,
    desktopHPadding: LinearDimension
) = Grid(cols = "1fr 1fr", rows = "auto") {
    css {
        onDesktop { padding(horizontal = desktopHPadding) }
    }
    systemPermits.forEach {
        Switch(
            name = "permits",
            value = it.toString(),
            label = it.toString(),
            checked = userPermits.contains(Permit.DEV) || userPermits.contains(it),
            disabled = true
        )
    }
}