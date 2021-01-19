package tz.co.asoft

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val kfg = konfig()
    val namespace: String by kfg
    val dao = authorizationDao()
    val controller = AuthorizationControllerLocator(dao)
    val logger = Logger(ConsoleAppender())
    val fetcher = object : KeyFetcher {
        val key = SecurityKey(uid = "0", value = "test-key")
        override suspend fun all(): List<SecurityKey> = listOf(key)
        override suspend fun load(kid: String): SecurityKey = key
    }
    val authorizer: Authorizer = JWTAuthorizer(HS256Algorithm("secret"), listOf())

    val authZServer = authorizationRestServer(9010, fetcher, authorizer, logger, controller)
    val authNServer = authenticationRestServer(9020, fetcher, authorizer, logger, controller,dao)

    launch { authZServer.start() }
    launch { authNServer.start() }
    println("Works for $namespace")
}