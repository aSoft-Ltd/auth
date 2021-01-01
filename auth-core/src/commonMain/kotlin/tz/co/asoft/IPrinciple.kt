package tz.co.asoft

interface IPrinciple {
    val claims: List<String>
    val account: UserAccount

    fun has(claim: String): Boolean {
        if (claims.contains("systems.developer")) return true
        if (claims.contains(claim)) return true
        return false
    }
}