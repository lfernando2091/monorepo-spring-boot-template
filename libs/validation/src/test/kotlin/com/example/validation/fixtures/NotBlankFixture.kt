package com.example.validation.fixtures

import com.example.validation.annotations.NotBlank

data class NotBlankFixture(
    @NotBlank
    val valString: String,
    @NotBlank(
        "valStringCustom is blank",
        "property.is_blank"
    )
    val valStringCustom: String,
    @NotBlank
    val valCollection: Collection<String>,
    @NotBlank
    val valMap: Map<String, String>,
)

data class WithNullNotBlankFixture(
    @NotBlank
    val valString: String?,
    @NotBlank(
        "valStringCustom is blank",
        "property.is_blank"
    )
    val valStringCustom: String?,
    @NotBlank
    val valCollection: Collection<String>?,
    @NotBlank
    val valMap: Map<String, String>?,
)