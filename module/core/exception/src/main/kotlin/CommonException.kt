class CommonException(
    val code: String,
    override val message: String
) : RuntimeException(message) {

    constructor() : this(
        code = "500",
        message = "Unexpected Internal Server Error Occurred"
    )

    constructor(error: BaseError) : this(error.code, error.message)

    constructor(message: String) : this(code = "UNKNOWN", message = message)

}