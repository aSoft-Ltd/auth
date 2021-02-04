@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import io.ktor.application.*
import io.ktor.routing.*
import kotlinx.serialization.KSerializer

class ClaimsModule(
    override val controller: IRestController<Claim>,
    override val keyFetcher: KeyFetcher,
    override val verifier: (SecurityKey) -> JWTVerifier,
    override val version: String
) : IRestModule<Claim> {
    override val serializer: KSerializer<Claim> = Claim.serializer()
    override val root: String = "authorization"
    override val subRoot: String = "claims"
    override fun setRoutes(app: Application, log: Logger) = app.routing {
        
    }
}