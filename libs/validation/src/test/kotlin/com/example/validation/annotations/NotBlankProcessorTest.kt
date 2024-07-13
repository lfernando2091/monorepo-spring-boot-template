package com.example.validation.annotations

import com.example.validation.fixtures.NotBlankFixture
import com.example.validation.fixtures.WithNullNotBlankFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defNotBlankValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class NotBlankProcessorTest {
    @Test
    fun `errors not blank annotation`() {
        val notBlankFixture = NotBlankFixture(
            "       ",
            "  "
        )
        coreValidation.validation(
            notBlankFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 2,
            VALIDATION_HAS_ERRORS.format(2)
        )
        Assert.isTrue(
            defNotBlankValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valStringCustom",
                "valStringCustom is blank",
                "property.is_blank"
            ) == errors[1],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid not blank annotation`() {
        val notBlankFixture = NotBlankFixture(
            "abc 1  ",
            "a   "
        )
        coreValidation.validation(
            notBlankFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation not blank annotation ignore nulls`() {
        val withNullNotBlankFixture = WithNullNotBlankFixture(
            null
        )
        coreValidation.validation(
            withNullNotBlankFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}