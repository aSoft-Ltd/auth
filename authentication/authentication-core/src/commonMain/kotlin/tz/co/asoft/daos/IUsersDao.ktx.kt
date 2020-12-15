@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

suspend fun IUsersDao.load(loginId: String, password: String): User? {
    if (loginId.contains("@")) return load(Email(loginId), password)
    return load(Phone(loginId), password)
}