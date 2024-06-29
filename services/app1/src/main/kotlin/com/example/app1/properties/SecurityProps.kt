package com.example.app1.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix="security")
data class SecurityProps(
    var publicEndpoints: List<String> = emptyList()
)