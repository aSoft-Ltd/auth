@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

val SessionState.user: User? get() = (this as? SessionState.LoggedIn)?.user

val SessionState.account: UserAccount? get() = (this as? SessionState.LoggedIn)?.account