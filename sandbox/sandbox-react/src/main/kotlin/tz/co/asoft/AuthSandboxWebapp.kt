package tz.co.asoft

import react.RBuilder
import react.RProps
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

fun RBuilder.AuthSandboxWebapp(
    state: AuthenticationState.LoggedIn,
    moduleGroups: Map<String, List<NavMenu>>,
    modules: List<AbstractModuleRoute<out RProps>>
) = MainDrawerControllerConsumer { drawerController ->
    browserRouter {
        NavigationDrawer(
            drawerState = drawerController,
            drawer = {
                NavPane(
                    drawerController = drawerController,
                    moduleGroups = moduleGroups
                ) {
                    CompanyHeader("/logo.png", state.user.name)
                }
            },
            content = {
                switch {
                    for (module in modules) {
                        Authentication.systemPermits.addAll(module.permits.map { it.toPermit() })
                        route(path = module.path, exact = true, strict = true, render = module.render)
                    }
                }
            }
        )
    }
}