package tz.co.asoft.daos

import tz.co.asoft.IDao
import tz.co.asoft.InMemoryDao
import tz.co.asoft.UserAccountType
import tz.co.asoft.UserRole

class UserRolesTestDao : IDao<UserRole> by InMemoryDao("roles-dao") {
    init {
        for (i in 1..10) {
            create(UserRole(name = "User Role - $i", permits = UserAccountType.permits.randoms()))
        }
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