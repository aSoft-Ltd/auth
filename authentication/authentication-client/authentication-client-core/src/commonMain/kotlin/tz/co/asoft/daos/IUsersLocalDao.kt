@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUsersLocalDao {
    suspend fun save(token: String): String
    suspend fun load(): String?
    suspend fun delete()
}