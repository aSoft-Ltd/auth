package tz.co.asoft

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

class AuthenticationRestServer(
    port: Int = 8080,
    val keyFetcher: KeyFetcher,
    val logger: Logger,
    val authorizer: Authorizer,
    val authZController: AuthorizationControllerLocator,
    val authNController: AuthenticationControllerLocator,
    val moduleLocator: AuthenticationModuleLocator
) : RestServer(port, logger, listOf(moduleLocator.users, moduleLocator.clientApps, moduleLocator.accounts)) {
    override fun start() = embeddedServer(CIO, port) {
        installCORS()
        listOf(moduleLocator.users, moduleLocator.clientApps, moduleLocator.accounts).forEach {
            it.setRoutes(this, log)
            log.info("Endpoints at: :$port${it.path}")
        }
        routing {
            authorizeRoute(authorizer, logger)
            post("/${moduleLocator.users.path}/authenticate") {
                authenticateUserAccount(authNController.users, authNController.clientApps, authNController.accounts, authZController.claims, authorizer, logger)
            }
            get("/status") {
                call.respondText("Healthy")
            }
        }
    }.start(wait = true)
}