@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

sealed class AuthException(message: String) : Exception(message)

class UserAccountExistException(val ua: UserAccount) : AuthException("UserAccount {name: ${ua.name}, uid: ${ua.uid}} already exist")

class EmailExistException(val email: Email) : AuthException("Email ${email.value} already exist")

class PhoneExistException(val phone: Phone) : AuthException("Phone ${phone.value} already exist")