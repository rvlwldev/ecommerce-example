package com.ecommerce

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandService(private val port: UserCommandPort, private val passwordEncoder: PasswordEncoder) {

    private fun findByIdOrThrow(id: String) =
        port.findById(id) ?: throw CommonException(UserCommandError.USER_NOT_FOUND)

    @Transactional
    fun registerUser(command: UserCommand.Create): User = with(command) {
        if (port.isExistEmail(email))
            throw CommonException(UserCommandError.EMAIL_ALREADY_USED)

        try {
            val user = User(name, email, passwordEncoder.encode(command.password))
            return port.save(user)
        } catch (e: UserException) {
            throw UserCommandErrorMapper.toCommonException(e.error)
        }
    }

    @Transactional
    fun updateUserPassword(command: UserCommand.UpdatePassword): User = with(command) {
        val user = findByIdOrThrow(id)

        if (!passwordEncoder.matches(oldPassword, user.password))
            throw CommonException(UserCommandError.INCORRECT_PASSWORD)
        user.updatePassword(passwordEncoder.encode(newPassword))

        return port.save(user)
    }

    @Transactional
    fun updateUserName(command: UserCommand.UpdateName): User = with(command) {
        val user = findByIdOrThrow(id)
        user.updateName(name)
        return port.save(user)
    }

    @Transactional
    fun updateUserEmail(command: UserCommand.UpdateEmail): User = with(command) {
        val user = findByIdOrThrow(id)
        user.updateEmail(email)
        return port.save(user)
    }

    @Transactional
    fun withdrawUser(command: UserCommand.Remove): Boolean = with(command) {
        val user = findByIdOrThrow(id)
        return port.remove(user)
    }

}