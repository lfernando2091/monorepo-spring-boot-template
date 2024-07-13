package com.example.validation.fixtures

import com.example.validation.annotations.Max

data class MaxFixture(
    @Max(3)
    val valString: String,
    @Max(2)
    val valInt: Int,
    @Max(3)
    val valFloat: Float,
    @Max(
        3,
        "Error with valDouble",
        "value.value-bigger-than-max-value"
    )
    val valDouble: Double
)

data class MaxWithNullFixture(
    @Max(3)
    val valString: String?,
    @Max(2)
    val valInt: Int?,
    @Max(3)
    val valFloat: Float?,
    @Max(
        3,
        "Error with valDouble",
        "value.is-lower-that-min-val"
    )
    val valDouble: Double?
)