@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.entities.UserRole

class UserRolesTestRepo(
    private val dao: IDao<UserRole> = UserRolesTestDao()
) : IRepo<UserRole> by Repo(dao) {
    fun populate() = scope.later {
        for (i in 1..10) UserRole(
            name = "User Role - $i",
            permits = UserAccountType.permits.randoms()
        ).also { create(it).await() }
    }

    private fun <T> List<T>.randoms(): List<T> {
        val p1 = indices.random()
        val p2 = indices.random()
        if (p1 == p2) randoms()
        val start = minOf(p1, p2)
        val end = maxOf(p1, p2)
        return subList(start, end)
    }
}