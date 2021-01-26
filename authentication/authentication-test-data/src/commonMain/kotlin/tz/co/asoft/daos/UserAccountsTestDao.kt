@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class UserAccountsTestDao : IDao<UserAccount> by InMemoryDao("user-account")