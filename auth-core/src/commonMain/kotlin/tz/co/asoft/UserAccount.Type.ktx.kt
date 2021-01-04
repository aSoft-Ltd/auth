@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

fun UserAccount.Type.permissions() = permissionGroups.flatMap { it.permissions }

fun UserAccount.Type.permissionNames() = permissions().map { it.title }

fun List<UserAccount.Type>.permissionGroups() = flatMap { it.permissionGroups }