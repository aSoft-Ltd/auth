package tz.co.asoft

data class SystemPermissionGroup(
    val name: String,
    val details: String,
    val permissions: Set<SystemPermission>
)