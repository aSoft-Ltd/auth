@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class AuthenticationServiceLocator(
    open val users: IUsersService,
    open val clientApps: IDao<ClientApp>,
    open val accounts: IDao<UserAccount>
)