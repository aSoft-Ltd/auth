package tz.co.asoft

/**
 * Converts this [String] into a [Permit]
 * If the string can't be converted, this extension function @throws an [Exception]
 */
fun String.toPermit(): Permit {
    val splits = when{
        contains(".") -> split(".")
        contains(":") -> split(":")
        else -> throw Exception("Can't split $this into slices in order to parse as a Permit")
    }
    if (splits.size == 4) {
        val scope = splits[3]
        val action = splits[2]
        val subRoot = splits[1]
        val root = splits[0]
        return Permit(root, subRoot, action, scope)
    }

    if (splits.size == 3) {
        val scope = splits[2]
        val action = splits[1]
        val root = splits[0]
        return Permit(root = root, subRoot = null, action = action, scope = scope)
    }

    throw Exception("Can't parse $this into a Permit")
}

fun String.toPermitOrNull() = try {
    toPermit()
} catch (e: Exception) {
    null
}