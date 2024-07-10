package com.example.validation.processors

import com.example.validation.annotations.Range
import com.example.validation.processors.base.CustomValidator

class RangeProcessor: CustomValidator<Range, Int> {
    override fun isValid(value: Int?): Boolean {
        TODO("Not yet implemented")
    }
}