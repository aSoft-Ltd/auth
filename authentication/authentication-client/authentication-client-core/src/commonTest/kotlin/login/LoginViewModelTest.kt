package login

import tz.co.asoft.*
import tz.co.asoft.LoginFormViewModel.Intent
import tz.co.asoft.LoginFormViewModel.State
import kotlin.test.Test

class LoginViewModelTest {
    private val usersService = UsersFrontendTestService()
    private val vm = LoginFormViewModel(UsersRepo(usersService))
    private val populateLater = usersService.populate()

    @Test
    fun should_fail_to_log_in() = asyncTest {
        populateLater.await()
        vm.test(Intent.SignIn("account01@test.com", "04".toByteArray()))
        expect(vm).toBeIn<State.Error>()
    }

    private suspend fun login() {
        populateLater.await()
        vm.test(Intent.SignIn("account01@test.com", "01".toByteArray()))
        val state = expect(vm).toBeIn<State.Success>().state
        println(state.token)
    }

    @Test
    fun should_login_a_user_with_one_account() = asyncTest {
        login()
    }

    @Test
    fun should_succeed_in_logging_out() = asyncTest {
        login()
        usersService.signOut()
    }

    @Test
    fun should_login_a_user_with_more_than_one_account() = asyncTest {
        populateLater.await()
        vm.test(Intent.SignIn("account02@test.com", "02".toByteArray()))
        val ui = expect(vm).toBeIn<State.AccountSelection>()
        val u = ui.user
        vm.test(Intent.AuthenticateAccount(u.accounts.first(), u))
        expect(vm).toBeIn<State.Success>()
    }
}
