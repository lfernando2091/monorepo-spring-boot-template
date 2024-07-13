package com.example.validation.fixtures

import com.example.validation.annotations.Range

data class RangeFixture(
    @Range(2, 4)
    val valStringMin: String,
    @Range(2, 4)
    val valStringMax: String,
    @Range(2, 4)
    val valIntMin: Int,
    @Range(2, 4)
    val valIntMax: Int,
    @Range(2, 4)
    val valFloatMin: Float,
    @Range(2, 4)
    val valFloatMax: Float,
    @Range(
        2, 4,
        "Error with valDoubleMin",
        "value.lower-than-min-value"
    )
    val valDoubleMin: Double,
    @Range(
        2, 4,
        "Error with valDoubleMax",
        "value.grater-than-max-value"
    )
    val valDoubleMax: Double
)

data class RangeWithNullFixture(
    @Range(2, 4)
    val valStringMin: String?,
    @Range(2, 4)
    val valStringMax: String?,
    @Range(2, 4)
    val valIntMin: Int?,
    @Range(2, 4)
    val valIntMax: Int?,
    @Range(2, 4)
    val valFloatMin: Float?,
    @Range(2, 4)
    val valFloatMax: Float?,
    @Range(
        2, 4,
        "Error with valDoubleMin",
        "value.lower-than-min-value"
    )
    val valDoubleMin: Double?,
    @Range(
        2, 4,
        "Error with valDoubleMax",
        "value.grater-than-max-value"
    )
    val valDoubleMax: Double?
)