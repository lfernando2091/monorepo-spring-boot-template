package com.example.app1.exception

import com.example.app1.configs.AppConfig
import com.example.app1.exception.constants.Constants.GENERIC_ERROR_CODE
import com.example.app1.exception.constants.Constants.STATUS
import com.example.app1.exception.models.BaseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.MergedAnnotation
import org.springframework.core.annotation.MergedAnnotations
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class ExceptionControllerAdvice: DefaultErrorAttributes() {

    private val log: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)

    private val MESSAGE = "message"
    private val CAUSE = "cause"
    private val REQUEST_ID = "request_id"
    private val CODE = "code"
    private val ERROR_CODE = "error_code"
    private val ERRORS = "errors"

    override fun getErrorAttributes(
        request: ServerRequest,
        options: ErrorAttributeOptions?
    ): Map<String, Any?> {
        return assembleError(request)
    }

    private fun assembleError(request: ServerRequest): Map<String, Any?> {
        val serverException = getError(request)
        val responseStatusAnnotation = MergedAnnotations.from(serverException.javaClass,
            SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus::class.java)
        val errorStatus = determineHttpStatus(serverException, responseStatusAnnotation)
        return when(serverException) {
            is BaseException -> {
                log.error(serverException.reason, serverException.cause)
                mapOf(
                    MESSAGE to serverException.reason,
                    CAUSE to serverException.cause?.message,
                    REQUEST_ID to request.exchange().request.id,
                    ERROR_CODE to serverException.errorCode,
                    STATUS to errorStatus.value(),
                    ERRORS to serverException.errors
                )
            }
            else -> {
                log.error(serverException.message, serverException.cause)
                mapOf(
                    MESSAGE to serverException.message,
                    CAUSE to errorStatus.reasonPhrase,
                    REQUEST_ID to request.exchange().request.id,
                    ERROR_CODE to GENERIC_ERROR_CODE,
                    STATUS to errorStatus.value(),
                )
            }
        }
    }

    private fun determineHttpStatus(
        error: Throwable,
        responseStatusAnnotation: MergedAnnotation<ResponseStatus>
    ): HttpStatus {
        if (error is ResponseStatusException) {
            val httpStatus = HttpStatus.resolve(error.statusCode.value())
            if (httpStatus != null) return httpStatus
        }
        return responseStatusAnnotation
            .getValue(CODE, HttpStatus::class.java)
            .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    resourceProperties: WebProperties.Resources,
    applicationContext: ApplicationContext,
    serverCodecConfigure: ServerCodecConfigurer
): AbstractErrorWebExceptionHandler(errorAttributes, resourceProperties, applicationContext) {

    init {
        super.setMessageWriters(serverCodecConfigure.writers)
    }

    override fun getRoutingFunction(
        errorAttributes: ErrorAttributes?
    ): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), ::renderErrorResponse)

    fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse> {
        val errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val errorCode = errorPropertiesMap.getOrDefault(
            STATUS,
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        ) as Int
        return ServerResponse.status(HttpStatus.resolve(errorCode)!!)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(errorPropertiesMap))
    }
}