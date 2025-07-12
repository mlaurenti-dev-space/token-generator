package com.devspace.tokengenerator.service

import com.devspace.tokengenerator.config.SecretProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class JwtTokenService(private val props: SecretProperties) {

    fun generateToken(): String {
        val now = Instant.now()
        val exp = now.plusSeconds(props.expiration)
        val keyBytes = props.key.toByteArray()
        val keySpec = SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
        return Jwts.builder()
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(keySpec, SignatureAlgorithm.valueOf(props.algorithm))
            .compact()
    }

}