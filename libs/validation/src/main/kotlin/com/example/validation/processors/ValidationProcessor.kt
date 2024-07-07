package com.example.validation.processors

import com.example.validation.annotations.Length
import com.example.validation.annotations.NotBlank
import com.example.validation.annotations.NotNull
import com.example.validation.annotations.base.Processor
import com.example.validation.models.ValidationError
import com.example.validation.models.ValidationException
import com.example.validation.processors.base.CustomValidator
import com.example.validation.utils.findAnnotation
import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor

val AVAILABLE_ANNOTATIONS = setOf(
    NotBlank::class,
    NotNull::class,
    Length::class
)

private  val ANNOTATION_PROPS = setOf(
    "message",
    "errorCode"
)

private const val DEFAULT_ERROR_MESSAGE = "Unknown validation error"
private const val DEFAULT_ERROR_CODE = "unknown.validation_error"

fun validationProcessor(objectData: Any) {
    val clazz = objectData::class
    val errors = mutableListOf<ValidationError>()
    clazz
        .primaryConstructor
        ?.parameters
        ?.forEach { parameter ->
            AVAILABLE_ANNOTATIONS
                .forEach { expectedAnnotation ->
                    parameter
                        .findAnnotation(expectedAnnotation)
                        ?.let { annotation ->
                            val errorProps = propertyErrorProps(annotation)
                            val message = propertyByNameWithDefault(
                                ANNOTATION_PROPS.elementAt(0),
                                DEFAULT_ERROR_CODE,
                                errorProps,
                                annotation
                            )
                            val errorCode = propertyByNameWithDefault(
                                ANNOTATION_PROPS.elementAt(1),
                                DEFAULT_ERROR_CODE,
                                errorProps,
                                annotation
                            )
                            val propertyName = if (hasJsonProperty(parameter)) {
                                getJsonPropertyValue(parameter)
                            } else parameter.name!!

                            val rawValue = getParamValue(
                                objectData,
                                parameter.name!!
                            )

                            doValidation(
                                rawValue,
                                annotation,
                                errors,
                                propertyName,
                                message,
                                errorCode
                            )
                        }
                }
        }

    /*
    clazz.members
        .forEach { member ->
            println("annotations: ${member.annotations}")
            if (member is KProperty1<*, *>) {
                AVAILABLE_ANNOTATIONS
                    .forEach { expectedAnnotation ->
                    member
                        .findAnnotation(expectedAnnotation)
                        ?.let { annotation ->
                            val errorProps = propertyErrorProps(annotation)
                            val message = propertyByName(
                                ANNOTATION_PROPS.elementAt(0),
                                errorProps,
                                annotation
                            ).let {
                                if (it == null) DEFAULT_ERROR_MESSAGE
                                else it as String
                            }
                            val errorCode = propertyByName(
                                ANNOTATION_PROPS.elementAt(1),
                                errorProps,
                                annotation
                            ).let {
                                if (it == null) DEFAULT_ERROR_CODE
                                else it as String
                            }
                            val propertyName = if (hasJsonProperty(member)) {
                                "json_prop"
                            } else member.name
                            doValidation(
                                objectData,
                                member,
                                annotation,
                                errors,
                                propertyName,
                                message,
                                errorCode
                            )
                        }
                }
            }
    }
     */
    if (errors.isNotEmpty()) {
        throw ValidationException(errors)
    }
}

fun hasJsonProperty(
    property: KParameter
) = property
    .hasAnnotation<JsonProperty>()

fun getJsonPropertyValue(
    property: KParameter
) = property
.findAnnotation<JsonProperty>()?.value!!

fun doValidation(
    rawValue: Any?,
    annotation: Annotation,
    errors: MutableList<ValidationError>,
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
                errors.add(ValidationError(
                    propertyName,
                    message,
                    errorCode
                ))
            }
    }
}

@Suppress("UNCHECKED_CAST")
fun createInstance(
    clazz: KClass<out CustomValidator<*, *>>
) = clazz.objectInstance as? CustomValidator<Annotation, Any>
    ?: clazz.constructors.first().call() as CustomValidator<Annotation, Any>

fun propertyErrorProps(
    annotation: Annotation
) = annotation
    .annotationClass
    .members
    .filter {
        ANNOTATION_PROPS.contains(it.name) &&
                it is KProperty1<*, *>
    }.map { it as KProperty1<*, *> }

fun propertyByName(
    name: String,
    list: List<KProperty1<*, *>>,
    annotation: Annotation
): Any? = list
    .first { it.name == name }
    .getter.call(annotation)

fun propertyByNameWithDefault(
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

fun getParamValue(
    objectData: Any,
    name: String
) = objectData::class
    .members
    .find { it.name == name }
    ?.call(objectData)