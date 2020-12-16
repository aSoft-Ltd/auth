@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersFrontendService : IUsersService {
    val localDao: IUsersLocalDao
}