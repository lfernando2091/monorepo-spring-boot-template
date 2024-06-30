package com.example.app1.handlers

import com.example.app1.exception.models.BadRequestException
import com.example.app1.models.HomeReq
import com.example.app1.routers.HomeRouter.Companion.SIMPLE_PARAM
import com.example.app1.services.HomeService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class HomeHandler(
    val homeService: HomeService
) {
    suspend fun get(request: ServerRequest): ServerResponse =
        request
            .let {
                val simpleParam = request.queryParam(SIMPLE_PARAM)
                    .orElseThrow {
                        throw BadRequestException("Missing parameter '${SIMPLE_PARAM}'")
                    }
                ServerResponse
                    .ok()
                    .json()
                    .bodyValueAndAwait(
                        homeService.get(simpleParam)
                    )
            }

    suspend fun getWithError(request: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .json()
            .bodyValueAndAwait(
                homeService.getWithError()
            )

    suspend fun post(request: ServerRequest): ServerResponse =
        request
            .bodyToFlow(HomeReq::class)
            .map { body ->
                if(body.messageInput.isEmpty()) {
                    throw BadRequestException("Required property 'message_input'")
                }
                body
            }
            .single()
            .let { body ->
                ServerResponse
                    .ok()
                    .json()
                    .bodyValueAndAwait(
                        homeService.post(body)
                    )
            }
}