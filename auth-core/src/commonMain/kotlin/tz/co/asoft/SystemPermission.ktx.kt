package tz.co.asoft

fun Collection<SystemPermission>.hasPermit(name: String): Boolean {
    if (any { it.name == "system.developer" }) return true
    if (any { it.name == name }) return true
    return false
}

fun Collection<SystemPermission>.hasPermit(permit: SystemPermission) = hasPermit(permit.name)