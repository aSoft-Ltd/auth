@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.entities.UserRole

class UserRolesTestDao : IDao<UserRole> by UniqueNameInMemoryDao("role")