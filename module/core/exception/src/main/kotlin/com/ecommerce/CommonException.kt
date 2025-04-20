package com.ecommerce

open class CommonException(
    val code: String,
    override val message: String
) : RuntimeException(message) {

    constructor(code: Any, message: String) : this(code.toString(), message)
    constructor(message: String) : this("UNKNOWN", message)
    constructor() : this(500, "Unexpected Internal Server Error Occurred")

    class InvalidValue(message: String = "Invalid Input Format") : CommonException(400, message)
    class RequiredValue(message: String = "Required Value is Empty") : CommonException(400, message)
    class Unauthorized(message: String = "Unauthorized access") : CommonException(401, message)
    class Forbidden(message: String = "Forbidden Data") : CommonException(401, message)
    class NotFound(message: String = "Data Not Found") : CommonException(404, message)
    class AlreadyExists(message: String = "Duplicated Data") : CommonException(409, message)
    class InvalidState(message: String = "Invalid Data State, Already Done Or Cancelled") : CommonException(409, message)

}