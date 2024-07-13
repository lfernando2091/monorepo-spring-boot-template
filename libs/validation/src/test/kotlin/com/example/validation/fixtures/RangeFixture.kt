package com.example.validation.fixtures

import com.example.validation.annotations.Range
import com.example.validation.annotations.RangeDefinition

data class RangeFixture(
    @Range(RangeDefinition(1, 7))
    val valString: String
)