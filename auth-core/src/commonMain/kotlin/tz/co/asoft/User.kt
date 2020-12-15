@file:Suppress("PackageDirectoryMismatch")
@file:UseSerializers(LongAsStringSerializer::class)

package tz.co.asoft

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.builtins.LongAsStringSerializer

@Serializable
data class User(
    override var uid: String? = null,
    override val name: String,
    val password: String,
    val username: String? = null,
    val emails: List<String> = listOf(),
    val phones: List<String> = listOf(),
    val photoUrl: String? = null,
    val claimId: String,
    val status: Status = Status.SignedOut,
    val accounts: List<UserAccount>,
    val verifiedEmails: List<String> = listOf(),
    val verifiedPhones: List<String> = listOf(),
    val registeredOn: Long = Clock.System.now().toEpochMilliseconds(),
    val lastSeen: Long = Clock.System.now().toEpochMilliseconds(),
    override var deleted: Boolean = false
) : NamedEntity {
    init {
        if ((emails + phones).isEmpty()) throw Exception("A user must have a phone/email")
        emails.forEach { Email(it) }
        phones.forEach { Phone(it) }
    }

    enum class Status {
        Blocked,
        SignedIn,
        SignedOut
    }

    fun ref() = UserRef(
        uid = uid,
        name = name,
        claimId = claimId,
        photoUrl = photoUrl
    )
}