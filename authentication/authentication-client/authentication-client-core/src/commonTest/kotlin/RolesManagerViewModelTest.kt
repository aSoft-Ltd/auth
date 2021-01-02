import tz.co.asoft.*
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State
import tz.co.asoft.repos.UserRolesTestRepo
import kotlin.test.Test

class RolesManagerViewModelTest {
    private fun createPrinciple(vararg perms: String) = object : IUserPrinciple {
        override val account: UserAccount = UserAccount(
            name = "User Account 1",
            type = "Testing Account",
            scope = null
        )
        override val user: User = User(
            name = "test user 1",
            emails = listOf("user1@test.com"),
            phones = listOf(),
            accounts = listOf(account),
            password = "01"
        )
        override val claims = perms.toList()
    }

    private val repo = UserRolesTestRepo()
    private val withAccessPrinciple = createPrinciple("authentication.roles.create")
    private val withoutAccessPrinciple = createPrinciple("authentication.roles.test")
    private val withAccessVm = RolesManagerViewModel(repo, withAccessPrinciple, UserAccountType.permissionGroups)
    private val withoutAccessVm = RolesManagerViewModel(repo, withoutAccessPrinciple, UserAccountType.permissionGroups)
    private val populaterLater = repo.populate()

    @Test
    fun should_either_be_in_a_loading_state_or_showing_roles() = asyncTest {
        populaterLater.await()
        expect(withAccessVm).toBeInEither<State.Loading, State.Roles>()
        expect(withoutAccessVm).toBeInEither<State.Loading, State.Roles>()
    }

    @Test
    fun should_show_role_form() = asyncTest {
        populaterLater.await()
        withAccessVm.test(Intent.NewRoleForm)
        expect(withAccessVm).toBeIn<State.RoleForm>()
    }

    @Test
    fun should_display_error_showing_role_form_to_an_unpermitted_user() = asyncTest {
        populaterLater.await()
        withoutAccessVm.test(Intent.NewRoleForm)
        expect(withoutAccessVm).toBeIn<State.Error>()
    }

    @Test
    fun should_create_new_role() = asyncTest {
        populaterLater.await()
        val role = UserRole(name = "Test Role", permits = listOf())
        withAccessVm.test(Intent.CreateRole(role))
        val state = expect(withAccessVm).toBeIn<State.Roles>()
        expect(state.roles.map { it.uid }).toContain(role.uid)
    }

    @Test
    fun should_fail_to_create_role_fon_an_unpermitted_user() = asyncTest {
        populaterLater.await()
        val role = UserRole(name = "Test Role", permits = listOf())
        withoutAccessVm.test(Intent.CreateRole(role))
        expect(withoutAccessVm).toBeIn<State.Error>()
    }

    @Test
    fun should_fail_to_create_two_roles_with_same_names() = asyncTest {
        populaterLater.await()
        val role1 = UserRole(name = "Test Role 1", permits = listOf())
        withAccessVm.test(Intent.CreateRole(role1))
        expect(withAccessVm).toBeIn<State.Roles>()
        val role2 = role1.copy(uid = null)
        withAccessVm.test(Intent.CreateRole(role2))
        val state = expect(withAccessVm).toBeIn<State.Error>()
    }
}