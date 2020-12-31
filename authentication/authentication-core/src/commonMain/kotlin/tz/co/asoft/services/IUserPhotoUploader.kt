@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

interface IUserPhotoUploader {
    fun uploadPhoto(user: User, photo: File): Later<FileRef>
}