@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.json.Json

suspend fun JWTAlgorithm.createToken(principle: IPrinciple) = when (principle) {
    is IClientAppPrinciple -> createToken(principle.account, principle.app, principle.claims)
    is IUserPrinciple -> createToken(principle.account, principle.user, principle.claims)
    else -> throw Exception("Invalid principle")
}

suspend fun JWTAlgorithm.createToken(account: UserAccount, user: User, permits: Map<String, List<String>>) = createJWT {
    uid = user.uid ?: throw IllegalStateException("To create a token successfully, User(name=${user.name}) must have a non null id")
    userName = user.username ?: user.name
    accountName = account.name
    aid = account.uid ?: throw IllegalStateException("To create a token successfully, UserAccount(name=${account.name}) must have a non null id")
    claims = permits
    put("user", Mapper.decodeFromString(Json.encodeToString(User.serializer(), user)))
    put("account", Mapper.decodeFromString(Json.encodeToString(UserAccount.serializer(), account)))
}.token()

suspend fun JWTAlgorithm.createToken(account: UserAccount, app: ClientApp, permits: Map<String, List<String>>) = createJWT {
    uid = app.uid ?: throw IllegalStateException("To create a token successfully, ClientApp(name=${app.name}) must have a non null id")
    accountName = account.name
    userName = app.name
    aid = account.uid ?: throw IllegalStateException("To create a token successfully, UserAccount(name=${account.name}) must have a non null id")
    claims = permits
    put("app", Mapper.decodeFromString(Json.encodeToString(ClientApp.serializer(), app)))
    put("account", Mapper.decodeFromString(Json.encodeToString(UserAccount.serializer(), account)))
}.token()
