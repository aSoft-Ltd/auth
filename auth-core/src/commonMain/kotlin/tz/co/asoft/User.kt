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

    enum class Permissions(
        override val title: String,
        override val details: String,
        override val needs: List<String> = listOf(),
    ) : ISystemPermission {
        Read(
            title = "authentication.users.read",
            details = "Grants access to view/edit users in the system"
        ),
        Create(
            title = "authentication.users.create",
            details = "Grants access to create different users for the system",
            needs = listOf(Read.title)
        ),
        Update(
            title = "authentication.users.update",
            details = "Grants access to update user information",
            needs = listOf(Read.title)
        ),
        Delete(
            title = "authentication.users.delete",
            details = "Grants access to delete users from the system",
            needs = listOf(Read.title)
        )
    }

    fun ref() = UserRef(
        uid = uid,
        name = name,
        photoUrl = photoUrl
    )
}