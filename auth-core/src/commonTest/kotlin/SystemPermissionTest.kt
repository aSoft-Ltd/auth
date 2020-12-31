import tz.co.asoft.SystemPermission
import kotlin.test.Test
import kotlin.test.assertEquals

class SystemPermissionTest {

    @Test
    fun can_create_a_set_of_no_repeated_permissions_successfully() {
        val perm1 = SystemPermission(name = "authorization.roles.create", "Creates new user roles")
        val perm2 = SystemPermission(name = "authorization.roles.create", "Creates new user roles")
        assertEquals(perm1, perm2)
        val permSet = setOf(perm1, perm2)
        assertEquals(1, permSet.size)
    }

    @Test
    fun system_permissions_can_group_themselves_correctly() {
        val permSet = setOf(
            SystemPermission("authorization.roles.create", "Can create new permission"),
            SystemPermission("authorization.roles.edit", "Can edit existing user roles"),
            SystemPermission("authentication.users.create", "Can create new users"),
            SystemPermission("authentication.users.edit", "Can edit new users")
        )

        val map = mapOf(
            "authorization" to setOf(
                SystemPermission("roles.create", "Can create new permission"),
                SystemPermission("roles.edit", "Can edit existing user roles"),
            ),
            "authentication" to setOf(
                SystemPermission("users.create", "Can create new users"),
                SystemPermission("users.edit", "Can edit new users")
            )
        )
    }
}