package com.example.app1.routers

import com.example.app1.handlers.HomeHandler
import com.example.app1.models.*
import com.example.app1.utils.Constants.EMPTY_PATH
import com.example.app1.utils.Constants.ID_PATH
import com.example.app1.utils.Constants.ID_VARIABLE
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.StringToClassMapItem
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class HomeRouter(
    val homeHandler: HomeHandler
) {
    @Bean
    @RouterOperations(
        //region => /example
        RouterOperation(
            path = "/${BASE_API}",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "op${TAG_API}Get",
                tags = [TAG_API],
                summary = "Basic endpoint",
                description = "This a simple endpoint.",
                parameters = [
                    Parameter(
                        name = SIMPLE_PARAM,
                        `in` = ParameterIn.QUERY,
                        description = "Simple param field",
                        required = false
                    )
                             ],
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
        //endregion
        //region => /example/with-error
        RouterOperation(
            path = "/${BASE_API}/with-error",
            method = [RequestMethod.GET],
            operation = Operation(
                operationId = "op${TAG_API}GetError",
                tags = [TAG_API],
                summary = "Exception endpoint",
                description = "This endpoint intentionally generates an exception.",
                responses = [
                    ApiResponse(
                        description = "Bad request",
                        responseCode = "400",
                        content = [
                            Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = Schema(
                                    title = "BadRequestDto",
                                    type = "object",
                                    properties = [
                                        StringToClassMapItem(
                                            key = "message",
                                            value = String::class
                                        ),
                                        StringToClassMapItem(
                                            key = "cause",
                                            value = String::class
                                        ),
                                        StringToClassMapItem(
                                            key = "request_id",
                                            value = String::class
                                        ),
                                        StringToClassMapItem(
                                            key = "error_code",
                                            value = String::class
                                        ),
                                        StringToClassMapItem(
                                            key = "status",
                                            value = Int::class
                                        )
                                    ]
                                )
                            )
                        ]
                    )
                ]
            )
        ),
        //endregion
        //region => /example/with-payload
        RouterOperation(
            path = "/${BASE_API}/with-payload",
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = "op${TAG_API}Post",
                tags = [TAG_API],
                summary = "Payload endpoint",
                description = "Endpoint with JSON payload example",
                requestBody = RequestBody(
                    required = true,
                    description = "Allowed payload",
                    content = [
                        Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = HomeReq::class)
                        )
                    ]
                ),
                responses = [
                    ApiResponse(
                        description = "Result payload",
                        responseCode = "200",
                        content = [
                            Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = Schema(implementation = HomeRes::class)
                            )
                        ]
                    )
                ]
            )
        ),
        //endregion
        //region => /example/{id}
        RouterOperation(
            path = "/${BASE_API}/${ID_PATH}",
            method = [RequestMethod.DELETE],
            operation = Operation(
                operationId = "op${TAG_API}Delete",
                tags = [TAG_API],
                summary = "Deletes operation",
                description = "Deletes an element created previously",
                parameters = [
                    Parameter(
                        name = ID_VARIABLE,
                        `in` = ParameterIn.PATH,
                        description = "Element Id",
                        required = true
                    )],
                responses = [
                    ApiResponse(
                        description = "Delete result",
                        responseCode = "200",
                        content = [
                            Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = Schema(implementation = IdRes::class)
                            )
                        ]
                    )
                ]
            )
        ),
        //endregion
        //region => /example/with-payload-validation
        RouterOperation(
            path = "/${BASE_API}/with-payload-validation",
            method = [RequestMethod.POST],
            operation = Operation(
                operationId = "op${TAG_API}PostVal",
                tags = [TAG_API],
                summary = "Validate payload",
                description = "The payload will be validated before processing",
                requestBody = RequestBody(
                    required = true,
                    description = "Allowed payload",
                    content = [
                        Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = HomeValidationReq::class)
                        )
                    ]
                ),
                responses = [

                ]
            )
        )
        //endregion
    )
    fun homeRoutes() = coRouter {
        "/${BASE_API}".nest {
            accept(MediaType.APPLICATION_JSON).nest{
                GET(EMPTY_PATH, homeHandler::get)
                GET("/with-error", homeHandler::getWithError)
                POST("/with-payload", homeHandler::post)
                DELETE(ID_PATH, homeHandler::del)
                POST("/with-payload-validation", homeHandler::postValidator)
            }
        }
    }

    companion object {
        const val BASE_API = "example"
        const val SIMPLE_PARAM = "simple_param"
        const val TAG_API = "Main API"
    }
}