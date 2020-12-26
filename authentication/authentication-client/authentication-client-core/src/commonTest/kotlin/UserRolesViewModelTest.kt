import tz.co.asoft.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRolesViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm = Authentication.viewModels.userRoleManager()

    @Test
    fun should_begin_with_a_loading_state() = asyncTest {
        assertEquals(vm.ui.value, UserRoleManagerViewModel.State.Loading("Loading"))
    }
}