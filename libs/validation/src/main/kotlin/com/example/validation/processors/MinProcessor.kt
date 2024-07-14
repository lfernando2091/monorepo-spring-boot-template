package com.example.validation.processors

import com.example.validation.annotations.Min
import com.example.validation.processors.base.CustomValidator
import java.math.BigDecimal
import java.math.BigInteger

class MinProcessor: CustomValidator<Min, Any> {

    private lateinit var annotation: Min

    override fun initialize(annotation: Min) {
        this.annotation = annotation
    }

    override fun isValid(value: Any?): Boolean =
        when(value) {
            is String -> annotation.value <= value.length
            is Int -> annotation.value <= value
            is Float -> annotation.value.toFloat() <= value
            is Double -> annotation.value.toDouble() <= value
            is Long -> annotation.value.toLong() <= value
            is Short -> annotation.value.toShort() <= value
            is BigInteger -> annotation.value.toBigInteger() <= value
            is BigDecimal -> annotation.value.toBigDecimal() <= value
            is Collection<*> -> annotation.value <= value.size
            is Map<*, *> -> annotation.value <= value.size
            else -> true
        }
}