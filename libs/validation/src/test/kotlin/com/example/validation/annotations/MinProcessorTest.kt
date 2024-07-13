package com.example.validation.annotations

import com.example.validation.fixtures.MinFixture
import com.example.validation.fixtures.MinWithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defMinValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class MinProcessorTest {
    @Test
    fun `errors min annotation`() {
        val minFixture = MinFixture(
            "ab",
            1,
            2.9f,
            2.9
        )
        coreValidation.validation(
            minFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 4,
            VALIDATION_HAS_ERRORS.format(errors.size)
        )
        Assert.isTrue(
            defMinValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defMinValidationError(
                "valInt"
            ) == errors[1],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defMinValidationError(
                "valFloat"
            ) == errors[2],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valDouble",
                "Error with valDouble",
                "value.is-lower-that-min-val"
            ) == errors[3],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid min annotation`() {
        val minFixture = MinFixture(
            "abcd",
            3,
            4.5f,
            3.1
        )
        coreValidation.validation(
            minFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation min annotation ignore nulls`() {
        val minWithNullFixture = MinWithNullFixture(
            null,
            null,
            null,
            null
        )
        coreValidation.validation(
            minWithNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}