package com.example.validation.models

class ValidationException(
    val errors: List<ValidationError>
) : Exception()

data class ValidationError(
    val fieldName: String,
    val message: String,
    val errorCode: String
)