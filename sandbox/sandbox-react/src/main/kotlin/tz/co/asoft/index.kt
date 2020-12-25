package tz.co.asoft

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import tz.co.asoft.setup.setupLogging
import tz.co.asoft.setup.setupTheme

val kfg by lazy { konfig() }

fun setupAuthSandbox(alg: JWTAlgorithm) {
    setupTheme()
    setupLogging()
    setupInMemoryAuth(UsersLocalDao(kfg["package"] as String), alg)
}

//object SandBoxAlgorithm : JWTAlgorithm("HS256", LinearKeyRotator(
//    maxKeys = 1,
//    source = InMemoryKeySource(),
//    generator = { SecurityKey(uid = "0", value = "sandbox") }
//), HS256Signer)

fun main() = document.getElementById("root").setContent {
    val inMemorySigner = object : JWTSigner {
        override fun sign(jwt: JWT, key: SecurityKey): JWT = jwt.copy(signature = key.value)
    }
    val inMemoryAlgo = JWTAlgorithm("inmemory", LinearKeyRotator(
        maxKeys = 1,
        source = InMemoryKeySource(),
        generator = { SecurityKey(value = "inmemorysecret") }
    ), inMemorySigner)

    console.debug(LinearKeyRotator::class.simpleName ?: "Linear Key Rotator was null")
    console.debug(JWTAlgorithm::class.simpleName ?: "To string")
    setupAuthSandbox(inMemoryAlgo)
    AuthSandbox(
        signInPageUrl = "/imgs/sign-up.jpg"
    )
}