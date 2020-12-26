package tz.co.asoft

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Deprecated("Consider using SystemPermits", replaceWith = ReplaceWith("SystemPermission"))
@Serializable(with = Permit.Companion::class)
data class Permit(
    val root: String,
    val subRoot: String?,
    val action: String,
    val scope: String
) {
    val qualifier get() = if (subRoot == null) root else "$root.$subRoot"

    val qualifierAction get() = "$qualifier.$action"

    companion object : KSerializer<Permit> {
        val DEV = Permit("*", "*", "*", "*")

        fun parse(s: String): Permit = s.toPermit()

        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Permit")

        override fun deserialize(decoder: Decoder) = decoder.decodeString().toPermit()

        override fun serialize(encoder: Encoder, value: Permit) = encoder.encodeString(value.toString())
    }

    override fun toString(): String = "$qualifier.$action.$scope"
}