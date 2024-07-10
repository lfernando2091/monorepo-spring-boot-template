package com.example.app1.models

import com.example.validation.annotations.Length
import com.example.validation.annotations.NotBlank
import com.example.validation.annotations.NotNull
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

data class HomeDto(
    val message: String
)

data class HomeReq(
    @JsonProperty("message_input")
    val messageInput: String
)

data class HomeValidationReq(
    @NotNull
    @NotBlank
    @Length(5, "Name must be 5 length", "name.invalid_length")
    val name: String,
    @NotNull("Telephone must not be null", "telephone.is_null")
    val telephone: String,
    @JsonProperty("message_input")
    @NotBlank("Message input must not be empty", "message_input.is_empty")
    val messageInput: String?
)

data class HomeRes(
    // removed JsonProperty for this property due snake case not required
    val id: String,
    @JsonProperty("message_input")
    val messageInput: String
)

data class IdRes(
    // removed JsonProperty for this property due snake case not required
    val id: String
)

class HomeValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean =
        HomeValidationReq::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty")
        val targetObject = target as HomeValidationReq
        if (targetObject.telephone?.length != 10) {
            errors.rejectValue("telephone", "telephone.invalid.size");
        }
    }

}