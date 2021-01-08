//@file:Suppress("PackageDirectoryMismatch")
//
//package tz.co.asoft
//
//import kotlinx.css.*
//import react.RBuilder
//import react.RProps
//import styled.css
//import tz.co.asoft.UsersManager.Props
//import tz.co.asoft.UsersManagerViewModel.Intent
//import tz.co.asoft.UsersManagerViewModel.State
//
//@JsExport
//class UsersManager private constructor() : VComponent<Props, Intent, State, UsersManagerViewModel>() {
//
//    class Props(
//        val principle: IUserPrinciple,
//        val locator: AuthenticationViewModelLocator
//    ) : RProps
//
//    override val viewModel by lazy { props.locator.usersManager(props.principle) }
//
//    private fun RBuilder.Form(
//        accounts: List<UserAccount.Type>,
//        onCancel: () -> Unit,
//        onSubmit: (name: String, email: String, phone: String, type: UserAccount.Type) -> Unit
//    ) = Grid {
//        css {
//            onDesktop {
//                padding(horizontal = 20.pct)
//            }
//        }
//        UserForm(
//            user = null,
//            accounts = accounts,
//            onCancel = onCancel,//{ post(Intent.ViewUsers(null)) },
//            onSubmit = onSubmit // { name, email, phone, role -> post(Intent.CreateUser(name, email, phone, role)) }
//        )
//    }
//
//    private fun RBuilder.ShowUsers(pager: Pager<User>) = Surface {
//        css {
//            onDesktop {
//                padding(horizontal = 10.pct)
//            }
//        }
//        PaginatedGrid(pager, cols = "1fr 1fr 1fr") {
//            css {
//                onDesktop { gridTemplateColumns = GridTemplateColumns("1fr 1fr") }
//                onMobile { gridTemplateColumns = GridTemplateColumns("1fr") }
//            }
//            UserView(it)
//        }
//    }
//
//    override fun RBuilder.render(ui: State): Any = Surface(margin = 0.5.em) {
//        when (ui) {
//            is State.Loading -> LoadingBox(ui.msg)
//            is State.Form -> Form(ui.accountTypes, ui.onCancel, ui.onSubmit)
//            is State.Users -> ShowUsers(ui.pager)
//            is State.Error -> ErrorBox(ui.exception)
//            State.Success -> SuccessBox()
//        }
//    }
//}
//
//fun RBuilder.UsersManager(
//    principle: IUserPrinciple,
//    locator: AuthenticationViewModelLocator,
//) = child(UsersManager::class.js, Props(principle, locator)) {}