@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.css.*
import react.RBuilder
import react.RProps
import styled.css
import tz.co.asoft.Authentication.viewModels.usersManager
import tz.co.asoft.UsersManagerViewModel.Intent
import tz.co.asoft.UsersManagerViewModel.State

@JsExport
class UsersManager private constructor() : VComponent<RProps, Intent, State, UsersManagerViewModel>() {
    override val viewModel by lazy { usersManager() }

    override fun componentDidMount() {
        super.componentDidMount()
        post(Intent.ViewUsers(null))
    }

    private fun RBuilder.Form(ui: State.Form) = Grid {
        css {
            onDesktop {
                padding(horizontal = 20.pct)
            }
        }
        UserForm(
            user = null,
            roles = ui.roles,
            onCancel = { post(Intent.ViewUsers(null)) },
            onSubmit = { name, email, phone, role ->
                post(Intent.CreateUser(name, email, phone, role))
            }
        )
    }

    private fun RBuilder.ShowUsers(loader: PagingSource<User>) = Surface {
        css {
            onDesktop {
                padding(horizontal = 10.pct)
            }
        }
//        PaginatedGrid(loader.pager(pageSize = 10), cols = "1fr 1fr 1fr") {
//            css {
//                onDesktop { gridTemplateColumns = GridTemplateColumns("1fr 1fr") }
//                onMobile { gridTemplateColumns = GridTemplateColumns("1fr") }
//            }
//            UserView(it)
//        }
        PaginatedTable(
            pager = loader.pager(20),
            columns = listOf(
                Column("Name") { it?.name ?: "firstname lastname" },
                Column("Email") { it?.emails?.firstOrNull() ?: "user@email.com" },
                Column("Phone") { it?.phones?.firstOrNull() ?: "+XXX XXX XXX XXX" },
                RenderColumn("Actions") {
                    ContainedButton("View", FaEye)
                }
            )
        )
    }

    override fun RBuilder.render(ui: State): Any = Surface(margin = 0.5.em) {
        when (ui) {
            is State.Loading -> Loader(ui.msg)
            is State.Form -> Form(ui)
            is State.Users -> ShowUsers(ui.source)
            is State.Error -> Error(ui.msg)
            State.Success -> Success("Success")
        }
    }
}

fun RBuilder.UsersManager() = child(UsersManager::class) {}