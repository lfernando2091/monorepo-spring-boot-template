package com.example.validation.processors.base

interface CustomValidator<A: Annotation, T> {
    fun initialize(annotation: A) { }
    fun isValid(value: T?): Boolean
}