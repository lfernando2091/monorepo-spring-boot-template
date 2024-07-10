package com.example.validation.annotations

import com.example.validation.fixtures.LengthFixture
import com.example.validation.fixtures.LengthWithNullFixture
import com.example.validation.models.ValidationError
import com.example.validation.stubs.defLengthValidationError
import com.example.validation.utils.ConstantsTest.VALIDATION_CUSTOM_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_DEFAULT_MESSAGE
import com.example.validation.utils.ConstantsTest.VALIDATION_HAS_ERRORS
import com.example.validation.utils.ConstantsTest.VALIDATION_WITH_ERRORS
import com.example.validation.utils.ConstantsTest.coreValidation
import org.junit.jupiter.api.Test
import org.springframework.util.Assert
import java.math.BigInteger

class LengthProcessorTest {

    @Test
    fun `errors length annotation`() {
        val lengthFixture = LengthFixture(
            "abc",
            1,
            "xyz",
            BigInteger.valueOf(123456),
            arrayOf("e1"),
            listOf("e1")
        )
        coreValidation.validation(
            lengthFixture
        )
        val errors = coreValidation.getErrors()
        Assert.notEmpty(errors, VALIDATION_WITH_ERRORS)
        Assert.isTrue(errors.size == 6,
            VALIDATION_HAS_ERRORS.format(6)
        )
        Assert.isTrue(
            defLengthValidationError(
                "valString"
            ) == errors[0],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defLengthValidationError(
                "valInt"
            ) == errors[1],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            ValidationError(
                "valStringCustomMessage",
                "My field message",
                "my_field.error_code"
            ) == errors[2],
            VALIDATION_CUSTOM_MESSAGE
        )
        Assert.isTrue(
            defLengthValidationError(
                "valBigInteger"
            ) == errors[3],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defLengthValidationError(
                "valArray"
            ) == errors[4],
            VALIDATION_DEFAULT_MESSAGE
        )
        Assert.isTrue(
            defLengthValidationError(
                "valList"
            ) == errors[5],
            VALIDATION_DEFAULT_MESSAGE
        )
    }

    @Test
    fun `valid length annotation`() {
        val lengthFixture = LengthFixture(
            "abc123",
            12,
            "xyz1234",
            BigInteger.valueOf(12345),
            arrayOf("e1", "e2"),
            listOf("e1", "e2")
        )
        coreValidation.validation(
            lengthFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }

    @Test
    fun `validation length annotation ignore nulls`() {
        val lengthFixture = LengthWithNullFixture(
            null,
            null,
            null,
            null
        )
        coreValidation.validation(
            lengthFixture
        )
        val errors = coreValidation.getErrors()
        Assert.isTrue(errors.isEmpty(),
            VALIDATION_HAS_ERRORS.format(0)
        )
    }
}