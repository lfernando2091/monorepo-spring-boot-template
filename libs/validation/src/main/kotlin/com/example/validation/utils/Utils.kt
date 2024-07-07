package com.example.validation.utils

import com.example.validation.processors.validationProcessor
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToFlow
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotations

suspend fun <T: Any> ServerRequest.bodyValidator(
    clazz: KClass<T>
): Flow<T> = bodyToFlow(clazz)
    .map { body ->
        validationProcessor(body)
        /*
        val errors = BeanPropertyBindingResult(
            body,
            clazz.java.name
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
         */
        body
    }

fun <T : Annotation> KProperty1<*, *>.findAnnotation(clazz: KClass<T>): T? =
    findAnnotations(clazz)
        .firstOrNull()