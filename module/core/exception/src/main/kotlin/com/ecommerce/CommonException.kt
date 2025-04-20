package com.ecommerce

open class CommonException(
    val code: String,
    override val message: String
) : RuntimeException(message) {

    constructor(code: Any, message: String) : this(code.toString(), message)
    constructor(message: String) : this("UNKNOWN", message)
    constructor() : this(500, "Unexpected Internal Server Error Occurred")

    open class InvalidValue(message: String = "Invalid Input Format") : CommonException(400, message)
    open class RequiredValue(message: String = "Required Value is Empty") : CommonException(400, message)
    open class Unauthorized(message: String = "Unauthorized access") : CommonException(401, message)
    open class Forbidden(message: String = "Forbidden Data") : CommonException(401, message)
    open class NotFound(message: String = "Data Not Found") : CommonException(404, message)
    open class AlreadyExists(message: String = "Duplicated Data") : CommonException(409, message)
    open class InvalidState(message: String = "Invalid Data State, Already Done Or Cancelled") : CommonException(409, message)

}