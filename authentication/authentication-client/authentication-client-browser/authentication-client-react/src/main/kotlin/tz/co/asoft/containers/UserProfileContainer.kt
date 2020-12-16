package tz.co.asoft.containers

import kotlinx.css.em
import kotlinx.css.padding
import react.RBuilder
import react.RProps
import styled.css
import tz.co.asoft.*
import tz.co.asoft.UserProfileContainerViewModel.Intent
import tz.co.asoft.UserProfileContainerViewModel.State
import tz.co.asoft.containers.UserProfileContainer.Props

private class UserProfileContainer : VComponent<Props, Intent, State, UserProfileContainerViewModel>() {
    override val viewModel by lazy { AuthReact.viewModel.userProfileContainer() }

    class Props(val uid: String, val builder: (User) -> List<Tab>) : RProps

    override fun componentDidMount() {
        super.componentDidMount()
        post(Intent.ViewProfile(props.uid))
    }


    override fun componentWillReceiveProps(nextProps: Props) {
        if (nextProps.uid != props.uid) {
            post(Intent.ViewProfile(nextProps.uid))
        }
    }

    override fun RBuilder.render(ui: State) = MainDrawerControllerConsumer { controller ->
        NavigationAppBar(
            drawerController = controller,
            left = { +"Profile" }
        )

        Grid {
            css { padding(0.5.em) }
            when (ui) {
                is State.Loading -> Loader(ui.msg)
                is State.Profile -> Tabs(
                    Tab("Basic") { UserInfo(ui.user) },
                    *props.builder(ui.user).toTypedArray()
                )
                is State.Error -> Error(ui.msg)
            }
        }
    }
}

fun RBuilder.UserProfileContainer(
    uid: String,
    builder: (User) -> List<Tab>
) = child(UserProfileContainer::class.js, Props(uid, builder)) {}