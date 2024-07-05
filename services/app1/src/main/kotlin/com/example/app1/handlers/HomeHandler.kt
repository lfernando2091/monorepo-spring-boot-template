package com.example.app1.handlers

import com.example.app1.exception.models.BadRequestException
import com.example.app1.models.HomeReq
import com.example.app1.models.HomeValidationReq
import com.example.app1.models.HomeValidator
import com.example.app1.routers.HomeRouter.Companion.SIMPLE_PARAM
import com.example.app1.services.HomeService
import com.example.app1.utils.id
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.reactive.function.server.*
import java.util.stream.Collectors

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
                if(body.messageInput.isBlank()) {
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

    suspend fun postValidator(request: ServerRequest): ServerResponse =
        request
            .bodyToFlow(HomeValidationReq::class)
            .map { body ->
                val validator = HomeValidator()
                val errors = BeanPropertyBindingResult(
                    body,
                    HomeValidationReq::class.java.name
                )
                validator.validate(body, errors)
                if(errors.allErrors.isNotEmpty()) {
                    throw BadRequestException(
                        "Wrong request",
                        errors.allErrors
                            .stream()
                            .map { it.code }
                            .collect(Collectors.joining(","))
                    )
                }
                body
            }
            .single()
            .let { body ->
                ServerResponse
                    .ok()
                    .json()
                    .bodyValueAndAwait("")
            }

    suspend fun del(request: ServerRequest): ServerResponse =
        request
            .let {
                ServerResponse
                    .ok()
                    .json()
                    .bodyValueAndAwait(
                        homeService.del(request.id)
                    )
            }
}