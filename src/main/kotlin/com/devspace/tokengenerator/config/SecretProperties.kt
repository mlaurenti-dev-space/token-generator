package com.devspace.tokengenerator.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "secret")
class SecretProperties {
    lateinit var key: String
    lateinit var algorithm: String
    var expiration: Long = 604800 // Default expiration time in seconds
}