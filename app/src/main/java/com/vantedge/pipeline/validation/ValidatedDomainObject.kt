package com.vantedge.pipeline.validation

data class ValidatedDomainObject<T>(
    val value: T,
    val trace: ValidationTrace
)
