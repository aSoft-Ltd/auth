package tz.co.asoft

val SystemPermission.qualifier get() = name.split(".").first()

fun Collection<SystemPermission>.groups(): Map<String, Set<SystemPermission>> = groupBy {
    it.qualifier
}.mapValues { (_, v) ->
    v.map { it.copy(name = it.name.replace("${it.qualifier}.", "")) }.toSet()
}