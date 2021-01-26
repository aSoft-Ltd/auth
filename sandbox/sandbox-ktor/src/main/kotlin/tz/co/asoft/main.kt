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
        val key = SecurityKey(uid = "0", value = "secret")
        override suspend fun all(): List<SecurityKey> = listOf(key)
        override suspend fun load(kid: String): SecurityKey = key
    }

    val authorizer: Authorizer = JWTAuthorizer(HS256Algorithm(fetcher.load("0").value), listOf())

    val verifier = { key: SecurityKey -> HS256Verifier(key.value) }

    val authZServer = authorizationRestServer(9010, fetcher, verifier, authorizer, logger, controller)
    val authNServer = authenticationRestServer(9020, fetcher, verifier, authorizer, logger, controller, dao)

    launch { authZServer.start() }
    launch { authNServer.start() }
    println("Works for $namespace")
}