package com.example.validation.annotations

import com.example.validation.fixtures.PatternFixture
import com.example.validation.fixtures.PatternWithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defRegexValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class PatternProcessorTest {
    @Test
    fun `errors pattern annotation`() {
        val patternFixture = PatternFixture(
            "abc",
            "abcd"
        )
        coreValidation.validation(
            patternFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 2,
            VALIDATION_HAS_ERRORS.format(errors.size)
        )
        Assert.isTrue(
            defRegexValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valStringCustomMsg",
                "Wrong email",
                "email-field.wrong-value"
            ) == errors[1],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid pattern annotation`() {
        val patternFixture = PatternFixture(
            "lexample@domail.com",
            "lexample@domail.com"
        )
        coreValidation.validation(
            patternFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation pattern annotation ignore nulls`() {
        val patternWithNullFixture = PatternWithNullFixture(
            null, null
        )
        coreValidation.validation(
            patternWithNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}