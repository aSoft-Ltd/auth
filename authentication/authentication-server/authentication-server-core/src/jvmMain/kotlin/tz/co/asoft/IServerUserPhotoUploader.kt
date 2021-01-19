@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

import java.io.InputStream

interface IServerUserPhotoUploader : IUserPhotoUploader {
    val directory: String
    suspend fun uploadPhoto(uid: String, ext: String, inputStream: InputStream)
}