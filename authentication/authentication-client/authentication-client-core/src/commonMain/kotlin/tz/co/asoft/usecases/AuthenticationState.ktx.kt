@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

val AuthenticationState.user: User? get() = (this as? AuthenticationState.LoggedIn)?.user

val AuthenticationState.account: UserAccount? get() = (this as? AuthenticationState.LoggedIn)?.account