@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface ITokenStorage {
    fun save(token: String): String
    fun load(): String?
    fun delete()
}