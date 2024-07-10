package com.example.validation.utils

import com.example.validation.processors.base.CoreValidation
import com.example.validation.processors.base.CoreValidationImpl

object ConstantsTest {
    val coreValidation: CoreValidation = CoreValidationImpl()
    const val VALIDATION_HAS_ERRORS = "Validation has %d errors"
    const val VALIDATION_WITH_ERRORS = "Validation has errors"
    const val VALIDATION_DEFAULT_MESSAGE = "Element has default error message"
    const val VALIDATION_CUSTOM_MESSAGE = "Element has custom error message"
}