package com.example.validation.fixtures

import com.example.validation.annotations.NotBlank

data class NotBlankFixture(
    @NotBlank
    val valString: String,
    @NotBlank(
        "valStringCustom is blank",
        "property.is_blank"
    )
    val valStringCustom: String
)

data class WithNullNotBlankFixture(
    @NotBlank
    val valString: String?
)