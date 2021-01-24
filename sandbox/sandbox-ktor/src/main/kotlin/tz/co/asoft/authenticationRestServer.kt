package tz.co.asoft

fun authenticationRestServer(
    port: Int,
    fetcher: KeyFetcher,
    verifier: (SecurityKey) -> JWTVerifier,
    authorizer: Authorizer,
    logger: Logger,
    authZController: AuthorizationControllerLocator,
    dao: AuthorizationDaoLocator
): AuthenticationRestServer {
    val service = authenticationService(dao)
    val authNController = AuthenticationControllerLocator(service)
    val module = AuthenticationModuleLocator(fetcher, verifier, authNController)
    return AuthenticationRestServer(
        port = port,
        authorizer = authorizer,
        logger = logger,
        authZController = authZController,
        authNController = authNController,
        moduleLocator = module
    )
}