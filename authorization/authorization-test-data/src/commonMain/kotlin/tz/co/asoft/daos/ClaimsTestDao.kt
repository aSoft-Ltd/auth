@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import tz.co.asoft.entities.Claim

class ClaimsTestDao : IDao<Claim> by InMemoryDao("")