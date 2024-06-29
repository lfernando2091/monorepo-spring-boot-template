package com.example.database

import com.example.database.repository.BooksRepo
import com.example.database.services.BookService
import com.example.database.services.BookServiceImpl
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import reactor.core.publisher.Mono

@Configuration
@AutoConfiguration
@EnableReactiveMongoRepositories(
    basePackages = ["com.example.database.repository"]
)
@EnableReactiveMongoAuditing
@EnableConfigurationProperties(ClientAutoConfigurationProps::class)
open class ClientAutoConfiguration(
    val properties: ClientAutoConfigurationProps
) {
    @Bean
    open fun auditorProvider(): ReactiveAuditorAware<String>? {
        return ReactiveAuditorAware { Mono.empty() }
    }

    @Bean
    @ConditionalOnMissingBean
    open fun bookService(
        bookRepo: BooksRepo
    ): BookService = BookServiceImpl(bookRepo)
}