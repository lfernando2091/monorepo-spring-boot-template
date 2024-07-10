package com.example.app1

import com.example.app1.models.HomeValidationReq
import com.example.validation.processors.base.CoreValidation
import com.example.validation.processors.base.CoreValidationImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemplateApplication

fun main(args: Array<String>) {
	runApplication<TemplateApplication>(*args)
//	val dd = HomeValidationReq(
//		"hola",
//		null,
//		"  "
//	)
//	val coreValidation: CoreValidation = CoreValidationImpl()
//	coreValidation.validation(dd)
//	coreValidation.getErrors().forEach { error ->
//		println("Validation error for ${error.fieldName}: ${error.message} (${error.errorCode})")
//	}
}