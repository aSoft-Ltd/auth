@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft.locators

import tz.co.asoft.ClientApp
import tz.co.asoft.IDao
import tz.co.asoft.IUsersService
import tz.co.asoft.UserAccount

open class AuthenticationServiceLocator(
    open val users: IUsersService,
    open val clientApps: IDao<ClientApp>,
    open val accounts: IDao<UserAccount>
)