package tz.co.asoft

fun SystemPermission(
    name: String,
    details: String,
    needs: List<String> = listOf()
): ISystemPermission = object : ISystemPermission {
    override val title = name
    override val details = details
    override val needs: List<String> = needs
}