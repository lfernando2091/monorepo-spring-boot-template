package com.example.validation.annotations

import com.example.validation.fixtures.NotNullFixture
import com.example.validation.fixtures.WithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defNotNullValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class NotNullProcessorTest {
    @Test
    fun `errors not nulls annotation`() {
        val notNullFixture = WithNullFixture(
            null,
            null
        )
        coreValidation.validation(
            notNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 2,
            VALIDATION_HAS_ERRORS.format(2)
        )
        Assert.isTrue(
            defNotNullValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valInt",
                "val int is null",
                "val_int.is_null"
            ) == errors[1],
            VALIDATION_CUSTOM_MESSAGE
        )
    }

    @Test
    fun `valid not nulls annotation`() {
        val notNullFixture = NotNullFixture(
            "   ",
            1
        )
        coreValidation.validation(
            notNullFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}