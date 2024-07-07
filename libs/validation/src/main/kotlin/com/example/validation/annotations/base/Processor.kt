package com.example.validation.annotations.base

import com.example.validation.processors.base.CustomValidator
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.ANNOTATION_CLASS
)
@Retention(AnnotationRetention.RUNTIME)
annotation class Processor(
    val processedBy: Array<KClass<out CustomValidator<*, *> >>
)