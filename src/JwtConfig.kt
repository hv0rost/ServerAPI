package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.*
import java.util.*

data class TokenKey(val email: String) : Principal

object JwtConfig {
    private const val secret = "TheCloud"
    private const val issuer = "Client"

    //private const val validityInMs = 36_000_00 * 24 // 1 day
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(email: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("email", email)
        /*.withExpiresAt(getExpiration()) */ // optional
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    /* private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)*/

}