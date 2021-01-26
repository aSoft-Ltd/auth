package roles

import tz.co.asoft.*
import tz.co.asoft.RolesManagerViewModel.Intent
import tz.co.asoft.RolesManagerViewModel.State
import tz.co.asoft.UserRole
import kotlin.test.Test

class RolesManagerViewModelWithoutPermissionTest {
    private val dao = UserRolesTestDao()
    private val repo = Repo(dao)
    private val principle = UserPrinciple()
    private val vm = RolesManagerViewModel(repo, principle, UserAccountType.permissionGroups)
    private val populateLater = dao.populate()

    @Test
    fun should_display_error_showing_role_form_to_an_unpermitted_user() = asyncTest {
        populateLater.await()
        vm.test(Intent.ViewRoleForm(null))
        expect(vm).toBeIn<State.Error>()
    }

    @Test
    fun principle_without_access_should_not_have_permission_to_create_a_new_role() {
        expect(principle.has(UserRole.Permissions.Create)).toBe(false)
    }

    @Test
    fun should_fail_to_create_role_fon_an_unpermitted_user() = asyncTest {
        populateLater.await()
        val role = UserRole(name = "Test Role", permits = listOf())
        vm.test(Intent.CreateRole(role))
        expect(vm).toBeIn<State.Error>()
    }
}