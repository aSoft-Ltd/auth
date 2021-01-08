//@file:Suppress("PackageDirectoryMismatch")
//
//package tz.co.asoft
//
//import react.RBuilder
//import react.RProps
//import tz.co.asoft.UserDetailsManager.Props
//import tz.co.asoft.UserDetailsManagerViewModel.Intent
//import tz.co.asoft.UserDetailsManagerViewModel.State
//
//private class UserDetailsManager : VComponent<Props, Intent, State, UserDetailsManagerViewModel>() {
//    override val viewModel by lazy { props.locator.userDetailsManager() }
//
//    class Props(val user: User, val locator: AuthenticationViewModelLocator) : RProps
//
//    override fun componentDidMount() {
//        super.componentDidMount()
//        post(Intent.ViewUser(props.user))
//    }
//
//    override fun RBuilder.render(ui: State): Any = when (ui) {
//        is State.Loading -> Loader(ui.msg)
//        is State.ShowDetails -> UserDetails(
//            user = ui.u,
//            onSignOut = { post(Intent.SignOut) }.takeIf { ui.u.uid == ui.viewer?.uid },
//            onChangePassword = { post(Intent.ViewPasswordForm(ui.u)) }.takeIf { ui.u.uid == ui.viewer?.uid },
//            onChangeBasicInfo = { post(Intent.ViewBasicInfoForm(ui.u)) }.takeIf { ui.u.uid == ui.viewer?.uid }
//        )
//        is State.ShowPasswordForm -> PasswordForm(
//            onCancel = { post(Intent.ViewUser(ui.user)) },
//            onSubmit = { oldPass, newPass -> post(Intent.ChangePassword(oldPass, newPass)) }
//        )
//        is State.ShowBasicInfoForm -> BasicInfoForm(
//            user = ui.user,
//            onCancel = { post(Intent.ViewUser(ui.user)) },
//            onSubmit = { name, email, phone -> post(Intent.EditBasicInfo(name, email, phone)) }
//        )
//        is State.Error -> Error(ui.msg)
//    }
//}
//
//fun RBuilder.UserDetailsManager(
//    user: User,
//    locator: AuthenticationViewModelLocator
//) = child(UserDetailsManager::class.js, Props(user, locator)) {}