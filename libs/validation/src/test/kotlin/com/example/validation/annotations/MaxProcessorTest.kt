package com.example.validation.annotations

import com.example.validation.fixtures.MaxFixture
import com.example.validation.fixtures.MaxWithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defMaxValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class MaxProcessorTest {
    @Test
    fun `errors max annotation`() {
        val maxFixture = MaxFixture(
            "abcd",
                    3,
            3.1f,
            3.01
        )
        coreValidation.validation(
            maxFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 4,
            VALIDATION_HAS_ERRORS.format(errors.size)
        )
        Assert.isTrue(
            defMaxValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defMaxValidationError(
                "valInt"
            ) == errors[1],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defMaxValidationError(
                "valFloat"
            ) == errors[2],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valDouble",
                "Error with valDouble",
                "value.value-bigger-than-max-value"
            ) == errors[3],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid max annotation`() {
        val maxFixture = MaxFixture(
            "abc",
            2,
            2.9f,
            2.01
        )
        coreValidation.validation(
            maxFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation max annotation ignore nulls`() {
        val maxWithNullFixture = MaxWithNullFixture(
            null, null, null, null
        )
        coreValidation.validation(
            maxWithNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}