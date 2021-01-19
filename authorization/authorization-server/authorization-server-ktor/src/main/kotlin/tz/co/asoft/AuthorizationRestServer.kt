package tz.co.asoft

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
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
        install(CORS) {
            method(HttpMethod.Options)
            method(HttpMethod.Patch)
            method(HttpMethod.Delete)
            header(HttpHeaders.XForwardedProto)
            header(HttpHeaders.AccessControlAllowOrigin)
            anyHost()
            allowCredentials = true
            maxAgeInSeconds = 1000
            allowNonSimpleContentTypes = true
        }
        listOf(moduleLocator.claims, moduleLocator.roles).forEach {
            it.setRoutes(this, logger)
            log.info("Endpoints at: :$port${it.path}")
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