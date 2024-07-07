package com.example.app1

import com.example.app1.models.HomeValidationReq
import com.example.validation.models.ValidationException
import com.example.validation.processors.validationProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemplateApplication

fun main(args: Array<String>) {
//	runApplication<TemplateApplication>(*args)
	val dd = HomeValidationReq(
		"hola5",
		"999",
		"a   "
	)
	try {
		validationProcessor(dd)
	} catch (e: ValidationException){
		e.errors.forEach { error ->
			println("Validation error for ${error.fieldName}: ${error.message} (${error.errorCode})")
		}
	}
}