package com.example.validation.processors

import com.example.validation.annotations.Length
import com.example.validation.processors.base.CustomValidator
import com.example.validation.utils.Constants.UNABLE_TO_PROCESS_WITH_ANNOTATION
import java.math.BigDecimal
import java.math.BigInteger

class LengthProcessor: CustomValidator<Length, Any> {

    private lateinit var annotation: Length

    override fun initialize(annotation: Length) {
        this.annotation = annotation
    }

    override fun isValid(value: Any?): Boolean =
        when(value) {
            is String -> value.length == this.annotation.value
            is Int,
            is UInt,
            is Double,
            is Float,
            is Short,
            is UShort,
            is Long,
            is ULong,
            is BigInteger,
            is BigDecimal ->
                countDigitsRegex(value.toString()) == this.annotation.value
            is Array<*> -> value.size == this.annotation.value
            is List<*> -> value.size == this.annotation.value
            else -> {
                if (null != value) {
                    println(
                        UNABLE_TO_PROCESS_WITH_ANNOTATION.format(
                            value::class.simpleName,
                            Length::class.simpleName
                        )
                    )
                }
                true
            }
        }

    private fun countDigitsRegex(value: String) =
        Regex("\\d")
            .findAll(value)
            .count()
}