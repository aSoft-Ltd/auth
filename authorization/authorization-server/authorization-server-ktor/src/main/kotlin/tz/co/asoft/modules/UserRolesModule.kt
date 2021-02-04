@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import io.ktor.application.*
import io.ktor.routing.*
import kotlinx.serialization.KSerializer

class UserRolesModule(
    override val controller: IRestController<UserRole>,
    override val keyFetcher: KeyFetcher,
    override val verifier: (SecurityKey) -> JWTVerifier,
    override val version: String
) : IRestModule<UserRole> {
    override val serializer: KSerializer<UserRole> = UserRole.serializer()
    override val root: String = "authorization"
    override val subRoot: String = "user-roles"
    override fun setRoutes(app: Application, log: Logger) = app.routing {

    }
}