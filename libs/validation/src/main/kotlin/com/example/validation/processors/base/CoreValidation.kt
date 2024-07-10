package com.example.validation.processors.base

import com.example.validation.annotations.Length
import com.example.validation.annotations.Min
import com.example.validation.annotations.NotBlank
import com.example.validation.annotations.NotNull
import com.example.validation.annotations.base.Processor
import com.example.validation.models.ValidationError
import com.example.validation.utils.findAnnotations
import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor

interface CoreValidation {
    fun validation(data: Any)
    fun getErrors(): List<ValidationError>
    fun includeAnnotations(
        annotations: Set<KClass<out Annotation>> = emptySet()
    )
}

class CoreValidationImpl: CoreValidation {
    private val DEFAULT_ERROR_MESSAGE = "Unknown validation error"
    private val DEFAULT_ERROR_CODE = "unknown.validation_error"
    private val DEFAULT_AVAILABLE_ANNOTATIONS = setOf(
        NotBlank::class,
        NotNull::class,
        Length::class,
        Min::class
    )
    private val MAIN_ANNOTATION_PROPS = setOf(
        "message",
        "errorCode"
    )
    private var extraAnnotations: Set<KClass<out Annotation>> = emptySet()

    private val errors = mutableListOf<ValidationError>()

    override fun validation(
        data: Any
    ) {
        val kClazz = data::class
        errors.clear()
        kClazz
            .primaryConstructor
            ?.parameters
            ?.forEach { parameter ->
                parameter.findAnnotations(
                    DEFAULT_AVAILABLE_ANNOTATIONS + this.extraAnnotations
                ).forEach { annotation ->
                    processParamAnnotation(
                        data,
                        annotation,
                        parameter
                    ) }
            }
    }

    override fun getErrors(): List<ValidationError> = errors

    override fun includeAnnotations(
        annotations: Set<KClass<out Annotation>>
    ) {
        this.extraAnnotations = annotations
    }

    private fun processParamAnnotation(
        data: Any,
        annotation: Annotation,
        parameter: KParameter
    ) {
        val errorProps = errorProps(annotation)
        val message = propertyByNameWithDefault(
            MAIN_ANNOTATION_PROPS.elementAt(0),
            DEFAULT_ERROR_MESSAGE,
            errorProps,
            annotation
        )
        val errorCode = propertyByNameWithDefault(
            MAIN_ANNOTATION_PROPS.elementAt(1),
            DEFAULT_ERROR_CODE,
            errorProps,
            annotation
        )
        val propertyName = if (hasJsonProperty(parameter)) {
            getJsonPropertyValue(parameter) ?: parameter.name!!
        } else parameter.name!!

        val rawValue = getParamValue(
            data,
            parameter.name!!
        )

        startValidation(
            rawValue,
            annotation,
            propertyName,
            message,
            errorCode
        )
    }

    private fun startValidation(
        rawValue: Any?,
        annotation: Annotation,
        propertyName: String,
        message: String,
        errorCode: String
    ) {
        val validationProcessor  = annotation
            .annotationClass
            .findAnnotation<Processor>()

        validationProcessor
            ?.processedBy
            ?.forEach { customValClazz ->
                val customValClazzInstance = createInstance(customValClazz)
                customValClazzInstance.initialize(annotation)
                if (!customValClazzInstance.isValid(rawValue)) {
                    this.errors.add(ValidationError(
                        propertyName,
                        message,
                        errorCode
                    ))
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    private fun createInstance(
        clazz: KClass<out CustomValidator<*, *>>
    ) = clazz.objectInstance as? CustomValidator<Annotation, Any>
        ?: clazz.constructors.first().call() as CustomValidator<Annotation, Any>

    private fun getParamValue(
        objectData: Any,
        name: String
    ) = objectData::class
        .members
        .find { it.name == name }
        ?.call(objectData)

    private fun hasJsonProperty(
        property: KParameter
    ) = property
        .hasAnnotation<JsonProperty>()

    private fun getJsonPropertyValue(
        property: KParameter
    ) = property
        .findAnnotation<JsonProperty>()?.value

    private fun errorProps(
        annotation: Annotation
    ) = annotation
        .annotationClass
        .members
        .filter {
            MAIN_ANNOTATION_PROPS.contains(it.name) &&
                    it is KProperty1<*, *>
        }.map { it as KProperty1<*, *> }

    private fun propertyByName(
        name: String,
        list: List<KProperty1<*, *>>,
        annotation: Annotation
    ): Any? = list
        .first { it.name == name }
        .getter.call(annotation)

    private fun propertyByNameWithDefault(
        name: String,
        default: String,
        list: List<KProperty1<*, *>>,
        annotation: Annotation
    ) = propertyByName(
        name,
        list,
        annotation
    ).let {
        if (it == null) default
        else it as String
    }
}