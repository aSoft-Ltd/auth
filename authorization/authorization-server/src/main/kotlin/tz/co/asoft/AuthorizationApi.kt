package tz.co.asoft

import tz.co.asoft.Authorization.repo

object AuthorizationApi {
    object controller {
        fun claims() = RestController(repo.claims())
        fun roles() = RestController(repo.roles())
    }

    object module {
        fun claims(keyFetcher: KeyFetcher) = RestModule(
            version = "v0.1.0",
            root = "authorization",
            subRoot = "claims",
            keyFetcher = keyFetcher,
            serializer = Claim.serializer(),
            controller = controller.claims()
        )

        fun roles(keyFetcher: KeyFetcher) = RestModule(
            version = "v0.1.0",
            root = "authorization",
            subRoot = "user-roles",
            keyFetcher = keyFetcher,
            serializer = UserRole.serializer(),
            controller = controller.roles()
        )
    }

    object service {
        fun authorization(
            port: Int = 8080,
            appenders: List<Appender>,
            keyFetcher: KeyFetcher,
            authorizer: Authorizer,
            authorizationDao: AuthorizationDao
        ): AuthorizationRestApi {
            Logging.init(*appenders.toTypedArray())
            Authorization.init(authorizationDao)
            return AuthorizationRestApi(
                logger = Logger.of("logger" to "Authorization Rest Api"),
                port = port,
                keyFetcher = keyFetcher,
                authorizer = authorizer,
                modules = listOf(module.roles(keyFetcher), module.claims(keyFetcher))
            )
        }
    }
}