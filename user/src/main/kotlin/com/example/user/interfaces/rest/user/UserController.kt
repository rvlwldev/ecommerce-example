package com.example.user.interfaces.rest.user

import com.example.user.domain.history.HistoryService
import com.example.user.domain.user.UserService
import com.example.user.interfaces.rest.core.BizRestController
import com.example.user.interfaces.rest.core.request.ListRequest
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val historyService: HistoryService,
) : BizRestController {
    @PostMapping
    fun create(@RequestBody body: UserRequest.Create) =
        toResponse(userService.create(body.id, body.name), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: String) =
        toResponse(userService.find(id))

    @GetMapping("/{id}/histories")
    fun history(@PathVariable("id") id: String, body: ListRequest = ListRequest()) =
        PageRequest.of(body.number, body.size)
            .let { request -> historyService.findUserHistoryList(id, request) }
            .let { data -> toResponse(data) }

    @PatchMapping("/{id}")
    fun updateName(@RequestBody body: UserRequest.Update) =
        toResponse(userService.updateName(body.id, body.name))
}