@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    override var uid: String? = null,
    override val name: String,
    val permits: List<String>,
    override var deleted: Boolean = false
) : NamedEntity {
    enum class Permissions(
        override val title: String,
        override val details: String,
        override val needs: List<String> = listOf(),
    ) : ISystemPermission {
        Read(
            title = "authorization.roles.read",
            details = "Grants access to edit user roles"
        ),
        Create(
            title = "authorization.roles.create",
            details = "Grants access to create different user roles for the system",
            needs = listOf(Read.title)
        ),
        Update(
            title = "authorization.roles.update",
            details = "Grants access to update user roles",
            needs = listOf(Read.title)
        ),
        Delete(
            title = "authorization.roles.delete",
            details = "Grants access to delete user roles from the system",
            needs = listOf(Read.title)
        )
    }
}