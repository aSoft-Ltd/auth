@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    override var uid: String? = null,
    override val name: String,
    val permits: List<Permit>,
    override var deleted: Boolean = false
) : NamedEntity