package com.example.app1.utils

import com.example.app1.exception.models.BadRequestException
import com.example.app1.utils.Constants.ID_VARIABLE
import com.example.validation.processors.base.CoreValidation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.Logger
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToFlow
import kotlin.reflect.KClass

val ServerRequest.id: String get() = pathVariable(ID_VARIABLE)

suspend fun <T: Any> ServerRequest.bodyValidator(
    clazz: KClass<T>,
    coreValidation: CoreValidation,
    log: Logger
): Flow<T> = bodyToFlow(clazz)
    .map { body ->
        coreValidation.validation(body)
        if(coreValidation.getErrors().isNotEmpty()) {
            log.error("Invalid request for: $body")
            log.error("Validation error: ${coreValidation.getErrors()}")
            throw BadRequestException(
                message = "Invalid json payload",
                cause = "Json payload has some issues, please review the 'errors' list.",
                errorCode = "invalid.payload",
                errors = coreValidation.getErrors()
            )
        }
        body
    }
