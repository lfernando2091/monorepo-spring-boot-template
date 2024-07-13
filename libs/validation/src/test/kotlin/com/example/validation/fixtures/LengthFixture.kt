package com.example.validation.fixtures

import com.example.validation.annotations.Length
import java.math.BigInteger

data class LengthFixture(
    @Length(6)
    val valString: String,
    @Length(2)
    val valInt: Int,
    @Length(
        7,
        "My field message",
        "my_field.error_code"
    )
    val valStringCustomMessage: String,
    @Length(5)
    val valBigInteger: BigInteger,
    @Length(2)
    val valArray: Array<String>,
    @Length(2)
    val valList: List<String>
)

data class LengthWithNullFixture(
    @Length(6)
    val valString: String?,
    @Length(2)
    val valInt: Int?,
    @Length(2)
    val valArray: Array<String>?,
    @Length(2)
    val valList: List<String>?
)