import tz.co.asoft.asyncTest
import tz.co.asoft.await
import tz.co.asoft.UserRolesTestDao
import tz.co.asoft.expect
import tz.co.asoft.toBe
import kotlin.test.Test

class UserRolesTest {
    private val dao = UserRolesTestDao()

    @Test
    fun should_contain_ten_user_roles() = asyncTest {
        dao.populate().await()
        val roles = dao.all().await()
        expect(roles.size).toBe(10)
    }
}