package tz.co.asoft

import kotlinx.coroutines.coroutineScope

class UsersController(
    private val service: IUsersService,
    override val uploader: IServerUserPhotoUploader = UserPhotoUploader()
) : IUsersController, IUsersService by service, IServerUserPhotoUploader by uploader {

    override suspend fun login(loginId: String, password: String) = if (loginId.contains("@")) {
        load(Email(loginId), password).await()
    } else {
        load(Phone(loginId), password).await()
    }

    override suspend fun load(loginIds: List<String>) = coroutineScope {
        val (emails, others) = loginIds.partition { it.contains("@") }
        val (phones, ids) = others.partition { it.toIntOrNull() != null }
        val a = loadUsersWithOneOf(emails, phones)
        val b = service.load(ids)
        a.await() + b.await()
    }
}