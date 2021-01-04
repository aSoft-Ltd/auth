@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class AuthModuleDao(
    val users: IUsersDao,
    val clientApps: IDao<ClientApp>,
    val accounts: IDao<UserAccount>,
    val claims: IDao<Claim>,
    val roles: IDao<UserRole>
)