import tz.co.asoft.*
import tz.co.asoft.LoginFormViewModel.Intent
import tz.co.asoft.LoginFormViewModel.State
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.expect

class LoginViewModelTest {
    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
    }

    private val vm = Authentication.viewModels.loginForm()

    @Test
    fun should_fail_to_log_in() = asyncTest {
        vm.test(Intent.SignIn("account01@test.com", SHA256.digest("04".toByteArray()).hex))
        assertEquals(vm.ui.value, State.ShowForm("account01@test.com"))
    }

    @Test
    fun should_login_a_user_with_one_account() = asyncTest {
        vm.test(Intent.SignIn("account01@test.com", SHA256.digest("01".toByteArray()).hex))
        assertEquals(State.Success, vm.ui.value)
        assertEquals(Authentication.state.value.user?.emails?.first(), "account01@test.com")
    }

    @Test
    fun should_login_a_user_with_more_than_one_account() = asyncTest {
        vm.test(Intent.SignIn("account02@test.com", SHA256.digest("02".toByteArray()).hex))
        assertTrue { vm.ui.value is State.AccountSelection }
        val ui = vm.ui.value as State.AccountSelection
        val user = ui.user
        vm.test(Intent.AuthenticateAccount(user.accounts.first(), user))
        expect("account02@test.com") { Authentication.state.value.user?.emails?.first() }
    }
}