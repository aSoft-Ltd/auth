package tz.co.asoft

data class SystemPermission(
    val name: String,
    val details: String,
    val needs: List<String> = listOf()
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