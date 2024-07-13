package com.example.validation.fixtures

import com.example.validation.annotations.Min

data class MinFixture(
    @Min(3)
    val valString: String,
    @Min(2)
    val valInt: Int,
    @Min(3)
    val valFloat: Float,
    @Min(
        3,
        "Error with valDouble",
        "value.is-lower-that-min-val"
    )
    val valDouble: Double
)

data class MinWithNullFixture(
    @Min(3)
    val valString: String?,
    @Min(2)
    val valInt: Int?,
    @Min(3)
    val valFloat: Float?,
    @Min(
        3,
        "Error with valDouble",
        "value.is-lower-that-min-val"
    )
    val valDouble: Double?
)