package tz.co.asoft

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable

internal fun Route.keyRoutes(fetcher: KeyFetcher, logger: Logger) {
    get("/authorization/keys/{kid}") {
        flow<Result<SecurityKey?>> {
            val kid = call.parameters["uid"] ?: error("kid is not provided")
            emit(Success(fetcher.load(kid)))
        }.catch {
            emit(
                Failure(
                    error = it.message ?: "Unkown error",
                    type = it::class.simpleName ?: "Unknown type",
                    reason = it.cause?.message
                ).toResult()
            )
        }.collect {
            send(call, logger, HttpStatusCode.OK, SecurityKey.serializer().nullable, it)
        }
    }

    get("/authorization/keys/all") {
        flow {
            emit(Success(fetcher.all()))
        }.catch {
            emit(
                Result.Failure(
                    error = it.message ?: "Unkown error",
                    type = it::class.simpleName ?: "Unknown type",
                    reason = it.cause?.message
                )
            )
        }.collect {
            send(call, logger, HttpStatusCode.OK, ListSerializer(SecurityKey.serializer()), it)
        }
    }
}