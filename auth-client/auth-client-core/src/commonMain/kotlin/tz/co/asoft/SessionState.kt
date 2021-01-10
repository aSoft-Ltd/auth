@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.json.Json

sealed class SessionState {
    object Unknown : SessionState()
    object LoggedOut : SessionState()
    class LoggedIn(val token: String) : SessionState(), IUserPrinciple {
        val jwt = JWT.parse(token)
        override val user = Json.decodeFromString(User.serializer(), Mapper.encodeToString(jwt.payload["user"] as Map<String, Any?>))
        override val account = Json.decodeFromString(UserAccount.serializer(), Mapper.encodeToString(jwt.payload["account"] as Map<String, Any?>))
        override val claims = jwt.payload.claimsOrNull ?: listOf()
    }
}