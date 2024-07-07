package com.example.validation.processors

import com.example.validation.annotations.Length
import com.example.validation.annotations.NotBlank
import com.example.validation.annotations.NotNull
import com.example.validation.annotations.base.Processor
import com.example.validation.models.ValidationError
import com.example.validation.models.ValidationException
import com.example.validation.processors.base.CustomValidator
import com.example.validation.utils.findAnnotation
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

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
    clazz.members
        .forEach { member ->
            if (member is KProperty1<*, *>) {
                AVAILABLE_ANNOTATIONS.forEach { nestedAnnotation ->
                    member
                        .findAnnotation(nestedAnnotation)
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
                            doValidation(
                                objectData,
                                member,
                                annotation,
                                errors,
                                member.name,
                                message,
                                errorCode
                            )
                        }
                }
            }
    }
    if (errors.isNotEmpty()) {
        throw ValidationException(errors)
    }
}

fun doValidation(
    objectData: Any,
    property: KProperty1<*, *>,
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
            val rawVal = property.getter.call(objectData)
            if (!customValClazzInstance.isValid(rawVal)) {
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