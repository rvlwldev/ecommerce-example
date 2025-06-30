package com.ecommerce.documentation.annotation.field

import io.swagger.v3.oas.annotations.media.Schema
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Target(FIELD, VALUE_PARAMETER)
@Retention(RUNTIME)
@MustBeDocumented
@Schema(required = true, description = "user unique id", example = "exampleX-USER-UUID-AsID-exampleUserIDx")
annotation class UserId