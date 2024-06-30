package com.example.app1.routers

import com.example.app1.handlers.HomeHandler
import com.example.app1.models.HomeDto
import com.example.app1.utils.Constants.EMPTY_PATH
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class HomeRouter(
    val homeHandler: HomeHandler
) {
    @Bean
    @RouterOperations(
        RouterOperation(
            path = "/${BASE_API}",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "op${TAG_API}Get",
                tags = [TAG_API],
                summary = "Gets $TAG_API",
                description = "Gets $TAG_API",
                responses = [
                    ApiResponse(
                        description = "$TAG_API result",
                        responseCode = "200",
                        content = [
                            Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = Schema(implementation = HomeDto::class)
                            )
                        ]
                    )
                ]
            )
        ),
        RouterOperation(
            path = "/${BASE_API}/with-error",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "op${TAG_API}GetError",
                tags = [TAG_API],
                summary = "Gets $TAG_API with Error",
                description = "This endpoint intentionally generates an exception.",
                responses = [
                    ApiResponse(
                        description = "$TAG_API result",
                        responseCode = "200",
                        content = [
                            Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = Schema(implementation = HomeDto::class)
                            )
                        ]
                    )
                ]
            )
        )
    )
    fun homeRoutes() = coRouter {
        "/${BASE_API}".nest {
            accept(MediaType.APPLICATION_JSON).nest{
                GET(EMPTY_PATH, homeHandler::get)
                GET("/with-error", homeHandler::getWithError)
            }
        }
    }

    companion object {
        const val BASE_API = "home"
        const val TAG_API = "Home"
    }
}