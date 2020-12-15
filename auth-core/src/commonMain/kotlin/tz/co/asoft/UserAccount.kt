@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
open class UserAccount(
    override var uid: String? = null,
    override val name: String,
    val claimId: String,
    override var deleted: Boolean = false
) : NamedEntity