@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class AuthenticationFrontendServiceLocator(
    override val users: IUsersFrontendService,
    override val clientApps: IDao<ClientApp>,
    override val accounts: IDao<UserAccount>
) : AuthenticationServiceLocator(users, clientApps, accounts)