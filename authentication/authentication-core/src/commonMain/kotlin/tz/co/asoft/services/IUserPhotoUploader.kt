@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUserPhotoUploader {
    suspend fun uploadPhoto(user: User, photo: File): FileRef
}