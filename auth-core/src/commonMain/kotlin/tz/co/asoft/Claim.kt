package tz.co.asoft

import kotlinx.serialization.Serializable

@Serializable
data class Claim(
    override var uid: String? = null,
    val permits: List<Permit>,
    override var deleted: Boolean = false
) : Entity