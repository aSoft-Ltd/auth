@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationServiceLocator(
    val users: IUsersFrontendService,
    val clientApps: IDao<ClientApp>,
    val accounts: IDao<UserAccount>
)