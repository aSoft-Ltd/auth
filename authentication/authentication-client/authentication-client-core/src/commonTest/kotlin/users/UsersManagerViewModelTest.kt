package users

import tz.co.asoft.*
import tz.co.asoft.UsersManagerViewModel.Intent
import tz.co.asoft.UsersManagerViewModel.State
import kotlin.test.Test

class UsersManagerViewModelTest {
    private val service = UsersFrontendTestService()
    private val repo = UsersRepo(service)
    private val principle = UserPrinciple(
        User.Permissions.Create
    )
    private val vm: UsersManagerViewModel = UsersManagerViewModel(repo, principle, UserAccountType.all())
    private val populateLater = service.populate()

    @Test
    fun should_be_loading_or_viewing_users() = asyncTest {
        expect(vm).toBeInEither<State.Loading, State.Users>()
    }

    @Test
    fun should_display_users_in_paginated_form() = asyncTest {
        populateLater.await()
        vm.test(Intent.ViewUsers(UsersManagerViewModel.USERS_PER_PAGE, null))
        expect(vm).toBeIn<State.Users>()
    }

    @Test
    fun should_be_able_to_display_form_to_create_user() = asyncTest {
        populateLater.await()
        vm.test(Intent.ViewForm)
        expect(vm).toBeIn<State.Form>()
    }

    @Test
    fun should_be_able_to_create_users() = asyncTest {
        populateLater.await()
        val email = "userx@test.com"
        vm.test(Intent.CreateUser("Test User", email, "255799999999", UserAccountType.TESTER))
        expect(vm).toBeIn<State.Users>()
        val user = service.load(email, SHA256.digest("123456".toByteArray()).hex).await()
        expect(user).toBeNonNull()
    }
}