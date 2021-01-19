@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class TokenStorage(private val storage: KeyValueStorage) : ITokenStorage {
    override fun save(token: String): String {
        storage.set("token", token)
        return token
    }

    override fun load(): String? = storage.get("token")

    override fun delete() {
        storage.clear()
    }
}