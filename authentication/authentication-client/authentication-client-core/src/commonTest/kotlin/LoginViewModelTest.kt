import kotlinx.coroutines.delay
import tz.co.asoft.*
import tz.co.asoft.LoginFormViewModel.Intent
import tz.co.asoft.LoginFormViewModel.State
import kotlin.test.*

class LoginViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm = Authentication.viewModels.loginForm()

    @Test
    fun should_fail_to_log_in() = asyncTest {
        vm.test(Intent.SignIn("account01@test.com", "04".toByteArray()))
        assertEquals(vm.ui.value, State.ShowForm("account01@test.com"))
    }

    private suspend fun login() {
        delay(10)
        vm.test(Intent.SignIn("account01@test.com", "01".toByteArray()))
        assertEquals(State.Success, vm.ui.value)
        assertEquals(Authentication.state.value.user?.emails?.first(), "account01@test.com")
    }

    @Test
    fun should_login_a_user_with_one_account() = asyncTest { login() }

    @Test
    fun should_succeed_in_logging_out() = asyncTest {
        login()
        Authentication.service.users.signOut().join()
        assertNull(Authentication.state.value.user)
        assertEquals(Authentication.state.value, AuthenticationState.LoggedOut)
    }

    @Test
    fun should_login_a_user_with_more_than_one_account() = asyncTest {
        vm.test(Intent.SignIn("account02@test.com", "02".toByteArray()))
        assertTrue { vm.ui.value is State.AccountSelection }
        val ui = vm.ui.value as State.AccountSelection
        val user = ui.user
        vm.test(Intent.AuthenticateAccount(user.accounts.first(), user))
        expect("account02@test.com") { Authentication.state.value.user?.emails?.first() }
    }
}
