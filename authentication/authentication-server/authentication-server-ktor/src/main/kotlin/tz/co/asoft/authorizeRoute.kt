package tz.co.asoft

import io.ktor.application.call
import io.ktor.features.origin
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.request.uri
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.util.getValue
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal fun Route.authorizeRoute(authorizer: Authorizer, log: Logger) = post("/authorization/authorize") {
    flow<Result<String>> {
        if (!authorizer.allowedUrls.contains(call.request.origin.url)) {
            throw Exception("${call.request.origin.url} is not in the list of permitted authorizers")
        }
        val params = call.receiveParameters()
        val principle: String by params
        call.respondText(authorizer.authorize(Json.decodeFromString(Principle.serializer(), principle)))
    }.catch {
        emit(it.toFailure())
    }.collect {
        send(call, log, HttpStatusCode.OK, String.serializer(), it)
    }
}