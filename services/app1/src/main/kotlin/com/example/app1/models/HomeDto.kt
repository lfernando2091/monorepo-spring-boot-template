package com.example.app1.models

import com.fasterxml.jackson.annotation.JsonProperty

data class HomeDto(
    val message: String
)

data class HomeReq(
    @JsonProperty("message_input")
    val messageInput: String
)

data class HomeRes(
    // removed JsonProperty for this property due snake case not required
    val id: String,
    @JsonProperty("message_input")
    val messageInput: String
)