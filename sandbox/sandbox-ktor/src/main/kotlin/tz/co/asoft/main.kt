package tz.co.asoft

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val kfg = konfig()
    val namespace: String by kfg
    val dao = authorizationDao()
    val fetcher = object : KeyFetcher {
        val key = SecurityKey(uid = "0", value = "test-key")
        override suspend fun all(): List<SecurityKey> = listOf(key)
        override suspend fun load(kid: String): SecurityKey = key
    }
    val logger = Logger(ConsoleAppender())
    val authorizer: Authorizer = JWTAuthorizer(HS256Algorithm("secret"), listOf())
    val contoller = AuthorizationControllerLocator(dao)
    val module = AuthorizationModuleLocator(fetcher, contoller)
    val server1 = AuthorizationRestServer(
        port = 8080,
        keyFetcher = fetcher,
        authorizer = authorizer,
        logger = logger,
        moduleLocator = module
    )
    val server2 = AuthorizationRestServer(
        port = 8081,
        keyFetcher = fetcher,
        authorizer = authorizer,
        logger = logger,
        moduleLocator = module
    )
    launch { server1.start() }
    launch { server2.start() }
    println("Works for $namespace + $module")
}