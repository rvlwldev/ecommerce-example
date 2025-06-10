package com.ecommerce

object UserCommandErrorMapper {
    private val map = mapOf(
        UserError.INVALID_NAME_LENGTH to UserCommandError.INVALID_NAME_LENGTH,
        UserError.NOT_EMPTY_NAME to UserCommandError.NOT_EMPTY_NAME,
        UserError.DUPLICATE_NAME to UserCommandError.DUPLICATE_NAME,
        UserError.INVALID_EMAIL to UserCommandError.INVALID_EMAIL,
        UserError.NOT_EMPTY_EMAIL to UserCommandError.NOT_EMPTY_EMAIL,
        UserError.DUPLICATE_EMAIL to UserCommandError.DUPLICATE_EMAIL,
        UserError.NOT_EMPTY_PASSWORD to UserCommandError.NOT_EMPTY_PASSWORD
    )

    fun toCommonException(domainError: UserError): CommonException =
        CommonException(map[domainError] ?: UserCommandError.INVALID_USER_COMMAND)
}