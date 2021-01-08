@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class ClaimsTestDao : IDao<Claim> by InMemoryDao("claim")