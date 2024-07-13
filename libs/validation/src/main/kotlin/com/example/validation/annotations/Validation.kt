package com.example.validation.annotations

import com.example.validation.annotations.base.Processor
import com.example.validation.processors.*

@Target(
    AnnotationTarget.VALUE_PARAMETER,
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
    AnnotationTarget.VALUE_PARAMETER,
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
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [LengthProcessor::class]
)
annotation class Length(
    val value: Int,
    val message: String = "Wrong field length",
    val errorCode: String = "field.invalid_length"
)

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [MinProcessor::class]
)
annotation class Min(
    val value: Int,
    val message: String = "Wrong expected minimal size",
    val errorCode: String = "field.invalid_min_size"
)

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [MaxProcessor::class]
)
annotation class Max(
    val value: Int,
    val message: String = "Wrong expected minimal size",
    val errorCode: String = "field.invalid_min_size"
)

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
@Processor(
    processedBy = [RangeProcessor::class]
)
annotation class Range(
    val value: RangeDefinition,
    val message: String = "Field out of ranges",
    val errorCode: String = "field.out-of-ranges"
)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.ANNOTATION_CLASS
)
@Retention(AnnotationRetention.RUNTIME)
annotation class RangeDefinition(
    val min: Int,
    val max: Int
)