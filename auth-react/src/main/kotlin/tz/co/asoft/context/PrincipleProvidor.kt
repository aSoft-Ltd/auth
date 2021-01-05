@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import react.RBuilder
import react.RHandler
import react.RProviderProps
import react.createContext

private val PrincipleContext = createContext<IUserPrinciple>()

fun RBuilder.PrincipleProvider(
    principle: IUserPrinciple,
    handler: RHandler<RProviderProps<IUserPrinciple>>
) = PrincipleContext.Provider(principle, handler)

fun RBuilder.PrincipleConsumer(handler: RBuilder.(IUserPrinciple) -> Unit) = PrincipleContext.Consumer(handler)