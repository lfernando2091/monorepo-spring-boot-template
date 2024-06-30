package com.example.app1.configs

import com.example.app1.properties.BasicAuthProps
import com.example.app1.properties.SecurityProps
import com.example.app1.utils.Constants.ANY_FILE_EXTENSION
import com.example.app1.utils.Constants.ANY_PATH
import com.example.app1.utils.Constants.GENERIC_ROLE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun basicAuthProperties(): BasicAuthProps = BasicAuthProps()

    @Bean
    fun securityProperties(): SecurityProps = SecurityProps()

    @Bean
    fun userDetailsService(
        basicAuthProps: BasicAuthProps
    ): MapReactiveUserDetailsService {
        val user: UserDetails = User.builder()
            .username(basicAuthProps.username)
            .password("{noop}${basicAuthProps.password}")
            .roles(GENERIC_ROLE)
            .build()
        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        securityProps: SecurityProps
    ): SecurityWebFilterChain = http
            .httpBasic {  }
            .formLogin { formLogin -> formLogin.disable() }
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .headers { header ->
                header.frameOptions(withDefaults())
            }
            .authorizeExchange { exchanges ->
                exchanges.pathMatchers(
                        *securityProps.publicEndpoints.toTypedArray()
                    ).permitAll()
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    .pathMatchers(ANY_FILE_EXTENSION).permitAll()
                    .pathMatchers(HttpMethod.PUT, ANY_PATH)
                    .hasRole(GENERIC_ROLE)
                    .pathMatchers(HttpMethod.POST, ANY_PATH)
                    .hasRole(GENERIC_ROLE)
                    .pathMatchers(HttpMethod.GET, ANY_PATH)
                    .hasRole(GENERIC_ROLE)
                    .pathMatchers(HttpMethod.DELETE, ANY_PATH)
                    .hasRole(GENERIC_ROLE)
                    .anyExchange().authenticated()
            }
            .build()
}