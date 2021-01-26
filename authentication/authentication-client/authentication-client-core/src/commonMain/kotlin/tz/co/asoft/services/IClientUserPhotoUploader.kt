@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IClientUserPhotoUploader : IUserPhotoUploader {
    fun uploadPhoto(user: User, photo: File): Later<FileRef>
}