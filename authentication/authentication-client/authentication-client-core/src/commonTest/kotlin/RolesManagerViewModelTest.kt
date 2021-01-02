import tz.co.asoft.*
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State
import tz.co.asoft.daos.UserRolesTestDao
import tz.co.asoft.repos.UserRolesTestRepo
import kotlin.test.Test

class RolesManagerViewModelTest {
    val normalPrinciple = object : IUserPrinciple {
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
        override val claims = listOf(
            "authentication.roles.test",
            "authentication.roles.create"
        )
    }
    private val repo = UserRolesTestRepo()
    private val vm = RolesManagerViewModel(repo, normalPrinciple, UserAccountType.permissionGroups)
    private val populaterLater = repo.populate()

    @Test
    fun should_either_be_in_a_loading_state_or_showing_roles() = asyncTest {
        populaterLater.await()
        expect(vm).toBeInEither<State.Loading, State.Roles>()
    }

    @Test
    fun should_show_role_form() = asyncTest {
        populaterLater.await()
        vm.test(Intent.NewRoleForm)
        expect(vm).toBeIn<State.RoleForm>()
    }

    @Test
    fun should_create_new_role() = asyncTest {
        populaterLater.await()
        val role = UserRole(name = "Test Role", permits = listOf())
        vm.test(Intent.CreateRole(role))
        val state = expect(vm).toBeIn<State.Roles>()
        expect(state.roles.map { it.uid }).toContain(role.uid)
    }

    @Test
    fun should_fail_to_create_two_roles_with_same_names() = asyncTest {
        populaterLater.await()
        val role1 = UserRole(name = "Test Role 1", permits = listOf())
        vm.test(Intent.CreateRole(role1))
        expect(vm).toBeIn<State.Roles>()
        val role2 = role1.copy(uid = null)
        vm.test(Intent.CreateRole(role2))
        val state = expect(vm).toBeIn<State.Error>()
    }
}