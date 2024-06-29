package com.example.app1.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

data class CorsAllowed(
    var origins: List<String>,
    var methods: List<String>,
    var headers: List<String>,
    var credentials: Boolean,
    var maxAge: Long
)

@Configuration
@ConfigurationProperties(prefix="cors")
data class CorsProps(
    var allowed: CorsAllowed = CorsAllowed(
        emptyList(),
        emptyList(),
        emptyList(),
        false,
        3600
    )
)