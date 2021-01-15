@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class Claim(
    override var uid: String? = null,
    val permits: List<String>,
    override var deleted: Boolean = false
) : Entity {
    enum class Permissions(
        override val title: String,
        override val details: String,
        override val needs: List<String> = listOf(),
    ) : ISystemPermission {
        Read(
            title = "authorization.claims.read",
            details = "Grants access to view/read claims"
        ),
        Create(
            title = "authorization.claims.create",
            details = "Grants access to create different claims for the system",
            needs = listOf(Read.title)
        ),
        Update(
            title = "authorization.claims.update",
            details = "Grants access to update claims",
            needs = listOf(Read.title)
        ),
        Delete(
            title = "authorization.claims.delete",
            details = "Grants access to delete claims from the system",
            needs = listOf(Read.title)
        ),
        Wipe(
            title = "authorization.claims.wipe",
            details = "Grants access to wipe claims from the system",
            needs = listOf(Read.title)
        )
    }
}