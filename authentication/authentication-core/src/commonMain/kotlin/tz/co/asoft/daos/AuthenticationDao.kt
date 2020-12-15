@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthenticationDao(
    val clientApps: IDao<ClientApp>,
    val accounts: IDao<UserAccount>
)