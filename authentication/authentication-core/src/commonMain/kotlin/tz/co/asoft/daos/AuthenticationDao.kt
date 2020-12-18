@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationDao(
    val users: IUsersDao,
    val clientApps: IDao<ClientApp>,
    val accounts: IDao<UserAccount>
)