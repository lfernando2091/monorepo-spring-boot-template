package com.example.validation.processors

import com.example.validation.annotations.NotNull
import com.example.validation.processors.base.CustomValidator

class NotNullProcessor: CustomValidator<NotNull, Any> {
    override fun isValid(value: Any?): Boolean =
        value != null
}