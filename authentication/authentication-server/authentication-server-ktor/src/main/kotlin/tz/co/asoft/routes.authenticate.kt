package tz.co.asoft

import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.getValue
import io.ktor.util.pipeline.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal suspend fun PipelineContext<Unit, ApplicationCall>.authenticateUserAccount(
    usersController: IRestController<User>,
    appController: IRestController<ClientApp>,
    accountsController: IRestController<UserAccount>,
    claimsController: IRestController<Claim>,
    authorizer: Authorizer,
    log: Logger
) {
    log.info("Authenticating at authentication/authenticate")
    flow<Result<String>> {
        val params = call.receiveParameters()
        val uid = params["user-id"] ?: throw Exception("User Id is not found in request. Make sure to supply user-id property in your request body")
        val aid = params["account-id"] ?: throw Exception("Account id is not found in request. Make sure to supply account-id property in your request")

        val user = usersController.load(uid).await() ?: throw Exception("User with id $uid is not found")
        val account = accountsController.load(aid).await() ?: throw Exception("Account with id $aid is not found")
        val claims = claimsController.load(Claim.getUserClaimId(aid, uid)).await() ?: throw Exception("Failed to load claims for User")

        val principle = Principle.UserPrinciple(user, account, claims.permits)
        emit(Success(authorizer.authorize(principle)))
    }.catch {
        log.error("Failed to authenticate", it)
        emit(Either.Right(it.asFailure()))
    }.collect {
        send(call, log, HttpStatusCode.OK.value, String.serializer(), it)
    }
}