@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class ClientApp(
    override var uid: String? = null,
    override val name: String,
    val secret: String,
    val account: UserAccount,
    val hosts: List<String>,
    override var deleted: Boolean = false
) : NamedEntity