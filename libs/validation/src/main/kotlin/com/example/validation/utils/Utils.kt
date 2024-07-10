package com.example.validation.utils

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotations

fun <T : Annotation> KProperty1<*, *>.findAnnotation(clazz: KClass<T>): T? =
    findAnnotations(clazz)
        .firstOrNull()

fun <T : Annotation> KParameter.findAnnotation(clazz: KClass<T>): T? =
    findAnnotations(clazz)
        .firstOrNull()

fun <T : Annotation> KParameter.findAnnotations(clazzSet: Set<KClass<out T>>): List<T> =
    clazzSet
        .mapNotNull { clazz ->
            findAnnotations(clazz)
                .firstOrNull()
        }