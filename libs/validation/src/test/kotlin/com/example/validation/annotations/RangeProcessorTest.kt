package com.example.validation.annotations

import com.example.validation.fixtures.RangeFixture
import com.example.validation.fixtures.RangeWithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defRangeValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class RangeProcessorTest {

    @Test
    fun `errors range annotation`() {
        val rangeFixture = RangeFixture(
            "a", "abcd1",
            1, 5,
            1.9f, 4.01f,
            1.9, 4.1
        )
        coreValidation.validation(
            rangeFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 8,
            VALIDATION_HAS_ERRORS.format(errors.size)
        )
        Assert.isTrue(
            defRangeValidationError(
                "valStringMin"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defRangeValidationError(
                "valStringMax"
            ) == errors[1],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defRangeValidationError(
                "valIntMin"
            ) == errors[2],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defRangeValidationError(
                "valIntMax"
            ) == errors[3],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defRangeValidationError(
                "valFloatMin"
            ) == errors[4],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defRangeValidationError(
                "valFloatMax"
            ) == errors[5],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valDoubleMin",
                "Error with valDoubleMin",
                "value.lower-than-min-value"
            ) == errors[6],
            VALIDATION_CUSTOM_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valDoubleMax",
                "Error with valDoubleMax",
                "value.grater-than-max-value"
            ) == errors[7],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid range annotation`() {
        val rangeFixture = RangeFixture(
            "ab", "abcd",
            2, 4,
            2f, 3.99f,
            2.01, 4.0
        )
        coreValidation.validation(
            rangeFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation range annotation ignore nulls`() {
        val rangeWithNullFixture = RangeWithNullFixture(
            null, null,
            null, null,
            null, null,
            null, null
        )
        coreValidation.validation(
            rangeWithNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}