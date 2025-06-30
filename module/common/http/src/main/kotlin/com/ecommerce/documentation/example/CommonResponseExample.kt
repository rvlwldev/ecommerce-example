package com.ecommerce.documentation.example

object CommonResponseExample {
    const val USER_NOT_FOUND = """
    {
        "status": 404,
        "message": "User Not Found.",
        "timestamp": "2025-01-01T12:34:56.789+09:00[Asia/Seoul]"
    }
    """

    const val INTERNAL_SERVER_ERROR = """
    {
        "status": 500,
        "message": "Unexpected Internal Server Error Occurred",
        "timestamp": "2025-01-01T12:34:56.789+09:00[Asia/Seoul]"
    }
    """
}