@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class UserAccount(
    override var uid: String? = null,
    override val name: String,
    val photoUrl: String? = null,
    val scope: String?,
    val type: String,
    override var deleted: Boolean = false
) : NamedEntity {
    data class Type(
        val name: String,
        val details: String,
        val permissionGroups: List<SystemPermissionGroup>
    )
}