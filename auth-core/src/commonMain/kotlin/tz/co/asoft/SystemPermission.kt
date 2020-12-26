package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class SystemPermission(
    val name: String,
    val details: String
) {
    companion object {
        val DEV = SystemPermissionGroup(
            name = "system.developer",
            details = "",
            permissions = setOf(
                SystemPermission(
                    name = "system.developer",
                    details = "Grant's permissions to every action on the system (Should only be used in development)"
                )
            )
        )
    }
}