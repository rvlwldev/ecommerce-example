package com.ecommerce.documentation.annotation.method

import com.ecommerce.CommonErrorResponse
import com.ecommerce.documentation.example.CommonResponseExample.USER_NOT_FOUND
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
@Retention(RUNTIME)
@ApiResponse(
    responseCode = "404",
    description = "User Not Found",
    content = [Content(
        "application/json",
        [ExampleObject(value = USER_NOT_FOUND)],
        Schema(implementation = CommonErrorResponse::class)
    )]
)
annotation class UserNotFoundResponseSpec