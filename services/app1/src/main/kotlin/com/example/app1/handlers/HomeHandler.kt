package com.example.app1.handlers

import com.example.app1.services.HomeService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class HomeHandler(
    val homeService: HomeService
) {
    suspend fun get(request: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .json()
            .bodyValueAndAwait(
                homeService.get()
            )

    suspend fun getWithError(request: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .json()
            .bodyValueAndAwait(
                homeService.getWithError()
            )
}