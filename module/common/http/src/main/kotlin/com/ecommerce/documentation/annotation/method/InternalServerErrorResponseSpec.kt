package com.ecommerce.documentation.annotation.method

import com.ecommerce.CommonErrorResponse
import com.ecommerce.documentation.example.CommonResponseExample.INTERNAL_SERVER_ERROR
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION, ANNOTATION_CLASS)
@Retention(RUNTIME)
@ApiResponse(
    responseCode = "500",
    description = "Unexpected Server Error",
    content = [Content(
        "application/json",
        [ExampleObject(value = INTERNAL_SERVER_ERROR)],
        Schema(implementation = CommonErrorResponse::class),
    )]
)
annotation class InternalServerErrorResponseSpec