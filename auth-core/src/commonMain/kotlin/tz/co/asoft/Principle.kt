package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
sealed class Principle {
    @Serializable
    data class UserPrinciple(val user: User, val account: UserAccount) : Principle()
    @Serializable
    data class ClientAppPrinciple(val app: ClientApp, val account: UserAccount) : Principle()
}