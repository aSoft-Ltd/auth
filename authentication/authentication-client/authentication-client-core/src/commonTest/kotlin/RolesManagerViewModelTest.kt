import tz.co.asoft.*
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State
import kotlin.test.Test

class RolesManagerViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm: RolesManagerViewModel = Authentication.viewModels.rolesManager()

    @Test
    fun should_either_be_in_a_loading_state_or_showing_roles() = asyncTest {
        expect(vm).toBeInEither<State.Loading, State.Roles>()
    }

    @Test
    fun should_show_role_form() = asyncTest {
        vm.test(Intent.NewRoleForm)
        expect(vm).toBeIn<State.RoleForm>()
    }

    @Test
    fun should_create_new_role() = asyncTest {
        val role = UserRole(name = "Test Role", permits = listOf())
        vm.test(Intent.CreateRole(role))
        val state = expect(vm).toBeIn<State.Roles>()
        expect(state.roles.map { it.uid }).toContain(role.uid)
    }

    @Test
    fun should_fail_to_create_two_roles_with_same_names() = asyncTest {
        val role1 = UserRole(name = "Test Role 1", permits = listOf())
        vm.test(Intent.CreateRole(role1))
        expect(vm).toBeIn<State.Roles>()
        val role2 = role1.copy(uid = null)
        vm.test(Intent.CreateRole(role2))
        val state = expect(vm).toBeIn<State.Error>()
    }
}