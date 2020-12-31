@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun IUsersService.editBasicInfo(u: User, name: String, email: Email, phone: Phone) = edit(
    u.copy(
        name = name,
        emails = mutableSetOf(email.value).apply { addAll(u.emails) }.toList(),
        phones = mutableSetOf(phone.value).apply { addAll(u.phones) }.toList()
    )
)