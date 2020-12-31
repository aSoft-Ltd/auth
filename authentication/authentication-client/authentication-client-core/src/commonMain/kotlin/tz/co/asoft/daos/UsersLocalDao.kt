@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

open class UsersLocalDao(private val db: IStorage) : IUsersLocalDao {
    override fun save(token: String): String {
        db.set("token", token)
        return token
    }

    override fun load(): String? = db.get("token")

    override fun delete() {
        db.clear()
    }
}