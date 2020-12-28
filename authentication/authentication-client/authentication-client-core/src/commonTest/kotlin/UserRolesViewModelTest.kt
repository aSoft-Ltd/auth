import tz.co.asoft.*
import tz.co.asoft.UserRoleManagerViewModel.State
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRolesViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm = Authentication.viewModels.userRoleManager()

    @Test
    fun should_begin_with_a_loading_state() = asyncTest {
        expect(vm).toBeIn(State.Loading("Loading"))
    }
}