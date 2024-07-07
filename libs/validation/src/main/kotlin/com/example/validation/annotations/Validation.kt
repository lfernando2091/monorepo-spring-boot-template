package com.example.validation.annotations

import com.example.validation.annotations.base.Processor
import com.example.validation.processors.LengthProcessor
import com.example.validation.processors.NotBlankProcessor
import com.example.validation.processors.NotNullProcessor

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [NotBlankProcessor::class]
)
annotation class NotBlank(
    val message: String = "Field must not be blank",
    val errorCode: String = "field.is_blank"
)

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [NotNullProcessor::class]
)
annotation class NotNull(
    val message: String = "Field must not be null",
    val errorCode: String = "field.is_null"
)

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [LengthProcessor::class]
)
annotation class Length(
    val value: Int,
    val message: String = "Field must be %s of length",
    val errorCode: String = "field.invalid_length"
)