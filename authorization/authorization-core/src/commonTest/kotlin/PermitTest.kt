import tz.co.asoft.Permit
import tz.co.asoft.toPermit
import kotlin.test.Test
import kotlin.test.assertEquals

class PermitTest {
    @Test
    fun should_have_a_fine_syntax() {
        val permit1 = Permit(root = "authentication", subRoot = "users", action = "read", scope = "*")
        val permit2 = "authentication.users.read.*".toPermit()
        assertEquals(permit1, permit2)
    }
}