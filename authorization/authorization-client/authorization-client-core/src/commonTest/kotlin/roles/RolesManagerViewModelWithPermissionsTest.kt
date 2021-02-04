package roles

import tz.co.asoft.*
import tz.co.asoft.ISystemPermission.Companion.global
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State
import tz.co.asoft.UserRole
import kotlin.test.Test

class RolesManagerViewModelWithPermissionsTest {
    private val dao = UserRolesTestDao()
    private val repo = Repo(dao)
    private val principle = UserPrinciple(
        UserRole.Permissions.Create,
        UserRole.Permissions.Update
    )
    private val vm = RolesManagerViewModel(repo, principle, permissionGroups)
    private val populateLater = dao.populate()

    @Test
    fun should_either_be_in_a_loading_state_or_showing_roles() = asyncTest {
        populateLater.await()
        expect(vm).toBeInEither<State.Loading, State.Roles>()
    }

    @Test
    fun should_show_role_form() = asyncTest {
        populateLater.await()
        vm.test(Intent.ViewRoleForm(null))
        expect(vm).toBeIn<State.RoleForm>()
    }

    @Test
    fun principle_with_access_should_have_permission_to_create_a_new_role() {
        expect(principle.has(UserRole.Permissions.Create, global)).toBe(true)
    }

    @Test
    fun should_edit_a_role_that_already_exists() = asyncTest {
        populateLater.await()
        val role = UserRole(name = "Test Role", permits = mapOf())
        vm.test(Intent.CreateRole(role))
        expect(vm).toBeIn<State.Roles>()
        vm.test(
            Intent.UpdateRole(
                role.copy(
                    permits = mapOf(
                        UserRole.Permissions.Create.title to listOf(global)
                    )
                )
            )
        )
        expect(vm).toBeIn<State.ShowRole>()
    }

    @Test
    fun should_create_new_role() = asyncTest {
        populateLater.await()
        val role = UserRole(name = "Test Role", permits = mapOf())
        vm.test(Intent.CreateRole(role))
        val state = expect(vm).toBeIn<State.Roles>()
        expect(state.roles.map { it.uid }).toContain(role.uid)
    }

    @Test
    fun should_fail_to_create_two_roles_with_same_names() = asyncTest {
        populateLater.await()
        val role1 = UserRole(name = "Test Role 1", permits = mapOf())
        vm.test(Intent.CreateRole(role1))
        expect(vm).toBeIn<State.Roles>()
        val role2 = role1.copy(uid = null)
        vm.test(Intent.CreateRole(role2))
        expect(vm).toBeIn<State.Error>()
    }
}