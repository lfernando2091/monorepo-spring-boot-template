package com.example.validation.models

data class ValidationError(
    val fieldName: String,
    val message: String,
    val errorCode: String
)