package tz.co.asoft

fun authorizationRestServer(
    port: Int,
    fetcher: KeyFetcher,
    authorizer: Authorizer,
    logger: Logger,
    controller: AuthorizationControllerLocator
): AuthorizationRestServer {
    val module = AuthorizationModuleLocator(fetcher, controller)
    return AuthorizationRestServer(
        port = port,
        keyFetcher = fetcher,
        authorizer = authorizer,
        logger = logger,
        moduleLocator = module
    )
}