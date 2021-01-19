@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface Authorizer {
    val allowedUrls: List<String>
    suspend fun authorize(principle: Principle): String
}