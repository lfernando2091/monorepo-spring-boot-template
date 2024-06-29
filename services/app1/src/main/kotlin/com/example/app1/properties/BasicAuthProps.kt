package com.example.app1.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix="basic-auth")
data class BasicAuthProps(
    var username: String = "",
    var password: String = ""
)