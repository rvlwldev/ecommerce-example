package com.ecommerce

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/members")
class UserRestController(private val service: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: UserRequest.Create): UserResponse.Member {
        val result = service.create(body.name, body.email, body.password)

        return UserResponse.Member(result)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody body: UserRequest.Login): UserResponse.Member {
        val result = service.find(body.email, body.password)

        return UserResponse.Member(result)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): UserResponse.Member {
        val result = service.find(id)

        return UserResponse.Member(result)
    }

    @PatchMapping("/cash")
    @ResponseStatus(HttpStatus.OK)
    fun updateCash(@RequestBody body: UserRequest.Cash): UserResponse.Member {
        val result = service.updateCash(body.uuid, body.amount)

        return UserResponse.Member(result)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun withdraw(@PathVariable id: UUID, @RequestBody body: UserRequest.Withdraw) {
        service.withdraw(id, body.password)
    }

}