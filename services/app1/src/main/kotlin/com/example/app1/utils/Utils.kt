package com.example.app1.utils

import com.example.app1.utils.Constants.ID_VARIABLE
import org.springframework.web.reactive.function.server.ServerRequest

val ServerRequest.id: String get() = pathVariable(ID_VARIABLE)
