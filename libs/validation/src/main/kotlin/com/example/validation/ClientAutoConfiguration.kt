package com.example.validation

import com.example.validation.processors.base.CoreValidation
import com.example.validation.processors.base.CoreValidationImpl
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfiguration
open class ClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    open fun coreValidator(): CoreValidation =
        CoreValidationImpl()
}