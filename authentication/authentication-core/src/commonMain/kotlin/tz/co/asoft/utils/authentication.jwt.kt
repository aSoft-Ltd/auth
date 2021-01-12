package tz.co.asoft.utils

import kotlinx.serialization.json.Json
import tz.co.asoft.*
import tz.co.asoft.entities.Claim

suspend fun JWTAlgorithm.createToken(account: UserAccount, user: User, claim: Claim) = createJWT {
    uid = user.uid ?: throw IllegalStateException("To create a token successfully, User(name=${user.name}) must have a non null id")
    userName = user.username ?: user.name
    accountName = account.name
    aid = account.uid ?: throw IllegalStateException("To create a token successfully, UserAccount(name=${account.name}) must have a non null id")
    claims = claim.permits
    put("user", Mapper.decodeFromString(Json.encodeToString(User.serializer(), user)))
    put("account", Mapper.decodeFromString(Json.encodeToString(UserAccount.serializer(), account)))
}.token()