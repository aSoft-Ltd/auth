package tz.co.asoft

class InMemoryTokenStorage : ITokenStorage {
    var data: String? = null
    override fun save(token: String): String {
        data = token
        return token
    }

    override fun load(): String?  = data

    override fun delete() {
        data = null
    }
}