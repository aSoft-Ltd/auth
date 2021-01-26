package tz.co.asoft

interface IUsersController : IRestController<User>, IUsersService, IServerUserPhotoUploader {
    val uploader: IServerUserPhotoUploader
    suspend fun login(loginId: String, password: String): User?
    suspend fun load(loginIds: List<String>): List<User>
}