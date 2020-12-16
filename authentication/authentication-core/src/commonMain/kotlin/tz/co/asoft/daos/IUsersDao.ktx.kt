@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

suspend fun IUsersDao.load(loginId: String, password: String) = if (loginId.contains("@")) {
    load(Email(loginId), password)
} else {
    load(Phone(loginId), password)
}
