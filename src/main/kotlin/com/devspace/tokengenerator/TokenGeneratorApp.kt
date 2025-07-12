package com.devspace.tokengenerator

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import picocli.CommandLine
import java.time.Instant
import java.util.Date
import java.util.concurrent.Callable
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

@CommandLine.Command (
    name = "TokenGenerator",
    mixinStandardHelpOptions = true,
    version = ["TokenGenerator 0.0"],
    description = ["Generates tokens for various purposes."]
)
class TokenGeneratorApp : Callable<Int> {

    @CommandLine.Option(
        names = ["--secret"],
        required = true,
        description = ["HMAC secret key for signing the JWT"]
    )
    lateinit var secret: String

    @CommandLine.Option(
        names = ["--sub"],
        required = true,
        description = ["JWT subject (e.g. PayRequest ID)"]
    )
    lateinit var subject: String

    @CommandLine.Option(
        names = ["--exp-minutes"],
        defaultValue = "60",
        description = ["JWT expiration time in minutes"]
    )
    var expMinutes: Long = 60

    override fun call(): Int {
        val now = Instant.now()
        val exp = now.plusSeconds(expMinutes * 60)
        val keyBytes = secret.toByteArray()
        val keySpec = SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
        val token = Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(keySpec, SignatureAlgorithm.HS256)
            .compact()

        println(token)
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val exitCode = picocli.CommandLine(TokenGeneratorApp()).execute(*args)
            exitProcess(exitCode)
        }
    }
}