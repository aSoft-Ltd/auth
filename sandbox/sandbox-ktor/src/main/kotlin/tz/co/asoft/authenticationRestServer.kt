package tz.co.asoft

fun authenticationRestServer(
    port: Int,
    fetcher: KeyFetcher,
    authorizer: Authorizer,
    logger: Logger,
    dao: AuthorizationDaoLocator
): AuthenticationRestServer {
    val service = authenticationService(dao)
    val controller = AuthenticationControllerLocator(service)
    val module = AuthenticationModuleLocator(fetcher, controller)
    return AuthenticationRestServer(
        port = port,
        keyFetcher = fetcher,
        authorizer = authorizer,
        logger = logger,
        moduleLocator = module
    )
}