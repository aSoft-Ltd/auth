@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.LinearDimension
import kotlinx.css.padding
import react.RBuilder
import styled.css

internal fun RBuilder.PermitsView(
    userPermits: Collection<String>,
    systemPermits: List<SystemPermissionGroup>,
    desktopHPadding: LinearDimension
) = Grid(cols = "1fr 1fr", rows = "auto") {
    css {
        onDesktop { padding(horizontal = desktopHPadding) }
    }
    for (group in systemPermits) Accordion(group.name) {
        for (permit in group.permissions) Switch(
            name = "permits",
            value = permit.name,
            label = permit.name,
            checked = userPermits.contains(permit.name),
            disabled = true
        )
    }
}