package tz.co.asoft

open class JWTAuthorizer(
    private val alg: JWTAlgorithm,
    override val allowedUrls: List<String>
) : Authorizer {
    override suspend fun authorize(principle: Principle) = alg.createJWT {
        putAll(principle.toKotlinMap(Principle.serializer()))
    }.token()
}