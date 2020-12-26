package tz.co.asoft.utils

import kotlinx.serialization.json.Json
import tz.co.asoft.*

suspend fun JWTAlgorithm.createToken(account: UserAccount, user: User) = createJWT {
    uid = user.uid ?: throw IllegalStateException("To create a token successfully, User(name=${user.name}) must have a non null id")
    userName = user.username ?: user.name
    accountName = account.name
    aid = account.uid ?: throw IllegalStateException("To create a token successfully, UserAccount(name=${account.name}) must have a non null id")
    claims = listOf("test")
    put("user", Mapper.decodeFromString(Json.encodeToString(User.serializer(), user)))
    put("account", Mapper.decodeFromString(Json.encodeToString(UserAccount.serializer(), account)))
}.token()