package com.example.validation.processors

import com.example.validation.annotations.NotBlank
import com.example.validation.processors.base.CustomValidator

class NotBlankProcessor: CustomValidator<NotBlank, String> {
    override fun isValid(value: String?): Boolean =
        value?.isNotBlank() ?: true
}