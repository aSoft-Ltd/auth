package tz.co.asoft

fun authenticationRestServer(
    port: Int,
    fetcher: KeyFetcher,
    authorizer: Authorizer,
    logger: Logger,
    authZController: AuthorizationControllerLocator,
    dao: AuthorizationDaoLocator
): AuthenticationRestServer {
    val service = authenticationService(dao)
    val authNController = AuthenticationControllerLocator(service)
    val module = AuthenticationModuleLocator(fetcher, authNController)
    return AuthenticationRestServer(
        port = port,
        keyFetcher = fetcher,
        authorizer = authorizer,
        logger = logger,
        authZController = authZController,
        authNController = authNController,
        moduleLocator = module
    )
}