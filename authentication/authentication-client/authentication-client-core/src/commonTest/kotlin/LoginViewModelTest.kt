import tz.co.asoft.*
import tz.co.asoft.LoginFormViewModel.Intent
import tz.co.asoft.LoginFormViewModel.State
import kotlin.test.Test

class LoginViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm by lazy { Authentication.viewModels.loginForm() }

    @Test
    fun should_fail_to_log_in() = asyncTest {
        vm.test(Intent.SignIn("account01@test.com", "04".toByteArray()))
        expect(vm).toBeIn<State.Error>()
    }

    private val user get() = Authentication.state.value.user

    private suspend fun login() {
        vm.test(Intent.SignIn("account01@test.com", "01".toByteArray()))
        expect(vm).toBeIn(State.Success)
        expect(user?.emails?.first()).toBe("account01@test.com")
    }

    @Test
    fun should_login_a_user_with_one_account() = asyncTest { login() }

    @Test
    fun should_succeed_in_logging_out() = asyncTest {
        login()
        Authentication.service.users.signOut()
        expect(user).toBeNull()
        expect(Authentication.state).toBe(AuthenticationState.LoggedOut)
    }

    @Test
    fun should_login_a_user_with_more_than_one_account() = asyncTest {
        vm.test(Intent.SignIn("account02@test.com", "02".toByteArray()))
        val ui = expect(vm).toBeIn<State.AccountSelection>()
        val u = ui.user
        vm.test(Intent.AuthenticateAccount(u.accounts.first(), u))
        expect(user?.emails?.first()).toBe("account02@test.com")
    }
}
