package tz.co.asoft

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.request.receiveText
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class UsersModule(
    version: String,
    override val controller: IUsersController,
    fetcher: KeyFetcher
) : RestModule<User>(
    version, "authentication", "users", fetcher, User.serializer(), controller,
    readPermission = User.Permissions.Read,
    createPermission = User.Permissions.Create,
    updatePermission = User.Permissions.Update,
    deletePermission = User.Permissions.Delete,
    wipePermission = User.Permissions.Delete
) {
    override fun setRoutes(app: Application, log: Logger) = super.setRoutes(app, log).apply {
        post("$path/login") {
            log.info("Login at $path/login")
            flow<Result<User?>> {
                val params = call.receiveParameters()
                val loginId = params["loginId"] ?: throw Exception("Invalid login Id")
                val password = params["password"] ?: throw Exception("Password not provided")
                emit(Success(controller.login(loginId, password)))
            }.catch {
                log.error("Failed to login at $path/login", it)
                emit(it.toFailure())
            }.collect {
                send(call, log, HttpStatusCode.OK, serializer.nullable, it)
            }
        }

        post("$path/load") {
            log.info("Checking user existence at $path/load")
            flow<Result<List<User>>> {
                val loginIds = Json.decodeFromString(ListSerializer(String.serializer()), call.receiveText())
                emit(Success(controller.load(loginIds)))
            }.catch {
                log.error("Failed to check user existence at $path/exists", it)
                emit(it.toFailure())
            }.collect {
                send(call, log, HttpStatusCode.OK, ListSerializer(serializer), it)
            }
        }

        post("$path/photo/{uid}") {
            log.info("Uploading user photo at $path/photo/{uid}")
            flow<Result<String>> {
                val uid = call.parameters["uid"] ?: throw Exception("Invalid user uid")
                call.receiveMultipart().forEachPart {
                    if (it is PartData.FileItem) {
                        val ext = File(it.originalFileName).extension
                        controller.uploader.uploadPhoto(uid, ext, it.streamProvider())
                        emit(Success("$path/photo/$uid.$ext"))
                    }
                }
            }.catch {
                log.error("Failed to upload user photo at $path/photo/{uid}")
                emit(it.toFailure())
            }.collect {
                send(call, log, HttpStatusCode.OK, String.serializer(), it)
            }
        }

        get("$path/photo/{uid}") {
            log.info("Sending user photo at $path/photo/{uid}")
            flow<File?> {
                val uid = call.parameters["uid"] ?: throw java.lang.Exception("Invalid uid")
                val file = File("/user-images/$uid")
                if (file.exists()) emit(file) else throw java.lang.Exception("Picture not found")
            }.catch {
                log.failure("Failed to send user photo:")
                emit(null)
            }.collect {
                if (it != null) {
                    call.respondFile(it)
                } else {
                    call.respondText("No Photo Yet")
                }
            }
        }
    }
}