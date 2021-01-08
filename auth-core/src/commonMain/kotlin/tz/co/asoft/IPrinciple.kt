package tz.co.asoft

interface IPrinciple {
    val claims: List<String>
    val account: UserAccount

    fun has(permission: String): Boolean {
        if (claims.contains("system.developer")) return true
        if (claims.contains(permission)) return true
        return false
    }

    fun has(permission: ISystemPermission): Boolean = has(permission.title)
}