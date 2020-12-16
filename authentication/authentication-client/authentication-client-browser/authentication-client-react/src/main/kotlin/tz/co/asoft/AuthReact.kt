package tz.co.asoft

object AuthReact {
    val viewModel = Authentication.viewModels

    val menus = listOf(
        NavMenu("Users", "/users", FaUserFriends) { true },
        NavMenu("User Roles", "/user-roles", FaUser) { true }
    )
}