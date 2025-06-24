package com.ecommerce

class UserException(val error: UserError, cause: Throwable? = null) :
    RuntimeException(error.name, cause)