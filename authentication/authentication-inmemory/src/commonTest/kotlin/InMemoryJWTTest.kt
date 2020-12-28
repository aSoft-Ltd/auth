import kotlinx.serialization.json.Json
import tz.co.asoft.*
import tz.co.asoft.LoginFormViewModel.Intent
import kotlin.test.Test

class InMemoryJWTTest {

    init {
        setupInMemoryAuth(InMemoryUsersLocalDao())
        Logging.init(ConsoleAppender())
    }

    @Test
    fun mapper_can_encode_null() {
        val map = mapOf(
            "admin" to mapOf(
                "user" to null
            ),
            "boolean" to true,
            "users" to listOf("test")
        )

        println(Mapper.encodeToString(map))

        val account = UserAccount(name = "Test Account", scope = null, type = "admin")
        val json = Json(EJson) {
            encodeDefaults = true
        }
        println(json.encodeToString(UserAccount.serializer(), account))
    }

    @Test
    fun should_create_a_signed_jwt() = asyncTest {
        val vm = Authentication.viewModels.loginForm()
        vm.test(Intent.SignIn("account01@test.com", "01".toByteArray()))
        expect(vm).toBeIn(LoginFormViewModel.State.Success)
    }
}