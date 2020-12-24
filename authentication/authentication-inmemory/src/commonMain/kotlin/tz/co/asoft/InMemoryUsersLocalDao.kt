package tz.co.asoft

class InMemoryUsersLocalDao : IUsersLocalDao {
    var data: String? = null
    override suspend fun save(token: String): String {
        data = token
        return token
    }

    override suspend fun load(): String?  = data

    override suspend fun delete() {
        data = null
    }
}