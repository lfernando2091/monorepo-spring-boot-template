package com.example.validation.processors

import com.example.validation.annotations.NotBlank
import com.example.validation.processors.base.CustomValidator

class NotBlankProcessor: CustomValidator<NotBlank, Any> {
    override fun isValid(value: Any?): Boolean = when(value){
        is String -> value.isNotBlank()
        is Collection<*> -> value.isNotEmpty()
        is Map<*, *> -> value.isNotEmpty()
        else -> true
    }
}