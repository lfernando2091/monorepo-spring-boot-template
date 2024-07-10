package com.example.validation.fixtures

import com.example.validation.annotations.NotNull

data class NotNullFixture(
    @NotNull
    val valString: String,
    @NotNull
    val valInt: Int,
)

data class WithNullFixture(
    @NotNull
    val valString: String?,
    @NotNull(
        "val int is null",
        "val_int.is_null"
    )
    val valInt: Int?,
)