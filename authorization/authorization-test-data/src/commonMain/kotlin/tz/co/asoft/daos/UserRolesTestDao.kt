@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class UserRolesTestDao : IDao<UserRole> by UniqueNameInMemoryDao("role")