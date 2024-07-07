package com.example.validation.processors

import com.example.validation.annotations.Length
import com.example.validation.processors.base.CustomValidator

class LengthProcessor: CustomValidator<Length, String> {

    private lateinit var annotation: Length

    override fun initialize(annotation: Length) {
        this.annotation = annotation
    }

    override fun isValid(value: String?): Boolean =
        value?.length == this.annotation.value
}