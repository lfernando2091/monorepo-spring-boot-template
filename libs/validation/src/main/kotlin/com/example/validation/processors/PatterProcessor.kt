package com.example.validation.processors

import com.example.validation.annotations.Pattern
import com.example.validation.processors.base.CustomValidator

class PatterProcessor: CustomValidator<Pattern, String> {
    private lateinit var annotation: Pattern

    override fun initialize(annotation: Pattern) {
        this.annotation = annotation
    }

    override fun isValid(value: String?): Boolean = if (value != null) {
        val regex = Regex(
            this.annotation.value,
            annotation.options.toSet()
        )
        regex matches value
    } else {
        true
    }
}