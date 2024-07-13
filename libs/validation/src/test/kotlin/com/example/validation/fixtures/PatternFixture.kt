package com.example.validation.fixtures

import com.example.validation.annotations.Pattern

data class PatternFixture(
    @Pattern(
        """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"""
    )
    val valString: String,
    @Pattern(
        value = """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""",
        options = [RegexOption.IGNORE_CASE],
        message = "Wrong email",
        errorCode = "email-field.wrong-value"
    )
    val valStringCustomMsg: String
)

data class PatternWithNullFixture(
    @Pattern(
        """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"""
    )
    val valString: String?,
    @Pattern(
        value = """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""",
        options = [RegexOption.IGNORE_CASE],
        message = "Wrong email",
        errorCode = "email-field.wrong-value"
    )
    val valStringCustomMsg: String?
)