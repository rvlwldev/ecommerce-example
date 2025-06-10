package com.ecommerce

enum class UserCommandError(
    override val code: String,
    override val status: Int,
    override val message: String
) : CommonError {

    INVALID_USER_COMMAND("USER_000", 500, "Invalid User Request."),

    NOT_EMPTY_PASSWORD("USER_001", 400, "Password is required."),
    INCORRECT_PASSWORD("USER_002", 401, "Password does not match."),
    USER_NOT_FOUND("USER_003", 404, "User Not Found."),
    EMAIL_ALREADY_USED("USER_004", 409, "Email already used"),

    INVALID_NAME_LENGTH("USER_100", 400, "Name must be at least 2 characters long."),
    NOT_EMPTY_NAME("USER_101", 400, "Name must not be empty."),
    DUPLICATE_NAME("USER_102", 409, "New name is same as current name."),

    INVALID_EMAIL("USER_200", 400, "Invalid Email."),
    NOT_EMPTY_EMAIL("USER_201", 400, "Email must not be empty."),
    DUPLICATE_EMAIL("USER_202", 409, "New email is same as current email.");

    companion object {
        private val map = mapOf(
            UserError.INVALID_NAME_LENGTH to INVALID_NAME_LENGTH,
            UserError.NOT_EMPTY_NAME to NOT_EMPTY_NAME,
            UserError.DUPLICATE_NAME to DUPLICATE_NAME,
            UserError.INVALID_EMAIL to INVALID_EMAIL,
            UserError.NOT_EMPTY_EMAIL to NOT_EMPTY_EMAIL,
            UserError.DUPLICATE_EMAIL to DUPLICATE_EMAIL,
            UserError.NOT_EMPTY_PASSWORD to NOT_EMPTY_PASSWORD
        )

        fun toCommonException(domainError: UserError): CommonException =
            CommonException(map[domainError] ?: INVALID_USER_COMMAND)
    }

}
