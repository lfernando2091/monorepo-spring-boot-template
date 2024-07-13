package com.example.validation.processors

import com.example.validation.annotations.Max
import com.example.validation.annotations.Min
import com.example.validation.annotations.Range
import com.example.validation.processors.base.CustomValidator

class RangeProcessor: CustomValidator<Range, Any> {
    private lateinit var annotation: Range
    private lateinit var minProcessor: CustomValidator<Min, Any>
    private lateinit var maxProcessor: CustomValidator<Max, Any>

    override fun initialize(annotation: Range) {
        this.annotation = annotation
        this.minProcessor = MinProcessor()
        this.minProcessor.initialize(Min(this.annotation.value.min))
        this.maxProcessor = MaxProcessor()
        this.maxProcessor.initialize(Max(this.annotation.value.max))
    }

    override fun isValid(value: Any?): Boolean =
        minProcessor.isValid(value)
                && maxProcessor.isValid(value)
}