package daos

import tz.co.asoft.*
import kotlin.test.Test

class RolesDaoTest {
    private val dao: IDao<UserRole> = InMemoryDao("user-roles")

    @Test
    fun should_create_user_roles() = asyncTest {
        val role = UserRole(
            name = "System Admin",
            permits = listOf("system.developer")
        )
        val newRole = dao.create(role).await()
        expect(newRole.uid).toBeNonNull()
        expect(dao.load(newRole.uid ?: "").await()).toBe(newRole)
    }
}