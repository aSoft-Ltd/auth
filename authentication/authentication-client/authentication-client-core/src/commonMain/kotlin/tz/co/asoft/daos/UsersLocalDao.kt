@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class UsersLocalDao(private val db: IStorage) : IUsersLocalDao {
    override suspend fun save(token: String): String {
        db.set("token", token)
        return token
    }

    override suspend fun load(): String? = db.get("token")

    override suspend fun delete() {
        db.clear()
    }
}