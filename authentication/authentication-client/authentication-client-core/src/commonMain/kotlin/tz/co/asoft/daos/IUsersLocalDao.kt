@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersLocalDao {
    fun save(token: String): String
    fun load(): String?
    fun delete()
}