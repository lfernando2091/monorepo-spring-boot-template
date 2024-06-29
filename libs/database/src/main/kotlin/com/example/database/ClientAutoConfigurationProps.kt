package com.example.database

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "database")
data class ClientAutoConfigurationProps(
    var simpleValue: String = ""
)