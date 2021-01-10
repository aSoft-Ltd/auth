package tz.co.asoft

class InMemoryKeySource : KeySource<SecurityKey> {
    val keys = mutableListOf<SecurityKey>()
    override suspend fun add(key: SecurityKey): SecurityKey {
        keys.add(key)
        return key
    }

    override suspend fun all(): List<SecurityKey> = keys

    override suspend fun load(kid: String): SecurityKey? = keys.find { it.uid == kid }

    override suspend fun remove(key: SecurityKey): SecurityKey {
        keys.remove(key)
        return key
    }
}