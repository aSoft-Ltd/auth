package tz.co.asoft

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

class AuthorizationRestServer(
    port: Int = 8080,
    val keyFetcher: KeyFetcher,
    val logger: Logger,
    val authorizer: Authorizer,
    val moduleLocator: AuthorizationModuleLocator
) : RestServer(port, logger, listOf(moduleLocator.claims, moduleLocator.roles)) {
    override fun start() = embeddedServer(CIO, port) {
        installCORS()
        listOf(moduleLocator.claims, moduleLocator.roles).forEach {
            it.setRoutes(this, log)
            log.info("Endpoints at: ${it.path}")
        }
        routing {
            keyRoutes(keyFetcher, logger)
            authorizeRoute(authorizer, logger)
            get("/status") {
                call.respondText("Healthy")
            }
        }
    }.start(wait = true)
}