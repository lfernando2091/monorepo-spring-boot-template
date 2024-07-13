package com.example.validation.stubs

import com.example.validation.models.ValidationError

fun defLengthValidationError(
    propertyName: String
) =
    ValidationError(
        propertyName,
        "Wrong field length",
        "field.invalid_length"
    )

fun defNotNullValidationError(
    propertyName: String
) = ValidationError(
    propertyName,
    "Field must not be null",
    "field.is_null"
)

fun defNotBlankValidationError(
    propertyName: String
) = ValidationError(
    propertyName,
    "Field must not be blank",
    "field.is_blank"
)

fun defMinValidationError(
    propertyName: String
) = ValidationError(
    propertyName,
    "Wrong expected minimal size",
    "field.invalid_min_size"
)
