package com.ecommerce

open class CommonException : RuntimeException {
    val code: String
    val status: Int

    constructor() : this("COMMON_999", 500, "Unexpected Internal Server Error Occurred")

    constructor(message: String) : this("UNKNOWN", 500, message)

    constructor(e: BaseErrorCode) : super(e.message) {
        this.code = e.code
        this.status = e.status
    }

    constructor(e: BaseErrorCode, message: String = e.message) : super(message) {
        this.code = e.code
        this.status = e.status
    }

    constructor(status: Int) : super() {
        this.code = "UNKNOWN"
        this.status = status
    }

    constructor(code: String, status: Int = 500, message: String) : super(message) {
        this.code = code
        this.status = status
    }

}
