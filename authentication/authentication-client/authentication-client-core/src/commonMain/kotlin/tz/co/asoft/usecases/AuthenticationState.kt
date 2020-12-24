@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

sealed class AuthenticationState {
    object Unknown : AuthenticationState()
    object LoggedOut : AuthenticationState()
    class LoggedIn(val token: String) : AuthenticationState() {
        val jwt = JWT.parse(token)

        init {
            console.debug(token)
            val accountJsonObj = jwt.payload["account"]
            val castedAccount = accountJsonObj as Map<String, Any>
            console.debug(
                "Login State",
                "account" to accountJsonObj,
                "casted" to castedAccount
            )
            console.debug("")
        }

        val user = EJson.decodeFromString(User.serializer(), Mapper.encodeToString(jwt.payload["user"] as Map<String, Any>))
        val account = EJson.decodeFromString(UserAccount.serializer(), Mapper.encodeToString(jwt.payload["account"] as Map<String, Any>))

        fun has(claim: String): Boolean {
            if (jwt.payload.claimsOrNull?.contains("*") == true) return true
            if (jwt.payload.claimsOrNull?.contains(claim) == true) return true
            return false
        }
    }
}