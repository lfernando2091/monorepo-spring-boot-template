package com.example.app1.configs

import com.example.app1.properties.BasicAuthProps
import com.example.app1.properties.CorsProps
import com.example.app1.utils.Constants.ANY_PATH
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class AppConfig {
    private val log: Logger = LoggerFactory.getLogger(AppConfig::class.java)

    @Bean
    fun corsProperties(): CorsProps = CorsProps()

    @Bean
    fun corsWebFilter(
        corsProps: CorsProps
    ): CorsWebFilter {
        val configuration = CorsConfiguration()
        val allowed = corsProps.allowed
        configuration.allowedOrigins = allowed.origins
        configuration.allowedMethods = allowed.methods
        configuration.allowedHeaders = allowed.headers
        configuration.allowCredentials = allowed.credentials
        configuration.maxAge = allowed.maxAge
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration(ANY_PATH, configuration)
        return CorsWebFilter(source)
    }
}