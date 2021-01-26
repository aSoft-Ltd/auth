@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class UsersRepo(private val service: IUsersFrontendService) : IUsersRepo, IUsersFrontendService by service