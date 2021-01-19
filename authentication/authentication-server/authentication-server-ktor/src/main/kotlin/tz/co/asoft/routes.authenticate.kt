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

internal suspend fun PipelineContext<Unit,ApplicationCall>.authenticateUserAccount(
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
        val claims = claimsController.load("$aid-user-$uid").await() ?: throw Exception("Failed to load claims for User")

        val principle = Principle.UserPrinciple(user, account, claims.permits)
        emit(Success(authorizer.authorize(principle)))

//        val newPrinciple = when (val p = Json.decodeFromString(Principle.serializer(), principle)) {
//            is Principle.UserPrinciple -> {
//                val uid = p.user.uid ?: error("Can't authenticate against a null uid for a user")
//                val user = usersController.load(uid).await() ?: error("Can't find user with uid $uid in our databases")
//                val aid = p.account.uid ?: error("Can't authenticate against a null uid for the provided account")
//                val account = accountsController.load(aid).await() ?: error("Can't find account with uid $uid in our databases")
//                Principle.UserPrinciple(user, account, listOf())
//            }
//            is Principle.ClientAppPrinciple -> {
//                val uid = p.app.uid ?: error("Can't authenticate against a null uid for a client app")
//                val app = appController.load(uid).await() ?: error("Can't find a client app with uid $uid in our databases")
//                val aid = p.account.uid ?: error("Can't authenticate against a null uid for the provided account")
//                val account = accountsController.load(aid).await() ?: error("Can't find account with uid $uid in our databases")
//                Principle.ClientAppPrinciple(app, account, listOf())
//            }
//        }
//        emit(Success(authorizer.authorize(newPrinciple)))
    }.catch {
        log.error("Failed to authenticate", it)
        emit(Either.Right(it.asFailure()))
    }.collect {
        send(call, log, HttpStatusCode.OK, String.serializer(), it)
    }
}