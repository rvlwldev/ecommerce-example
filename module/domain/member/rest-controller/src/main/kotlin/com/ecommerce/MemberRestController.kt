package com.ecommerce

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/members")
class MemberRestController(private val service: MemberService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: MemberRequest.Create): MemberResponse.Member {
        val result = service.create(body.name, body.email, body.password)

        return MemberResponse.Member(result)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody body: MemberRequest.Login): MemberResponse.Member {
        val result = service.find(body.email, body.password)

        return MemberResponse.Member(result)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): MemberResponse.Member {
        val result = service.find(id)

        return MemberResponse.Member(result)
    }

    @PatchMapping("/cash")
    @ResponseStatus(HttpStatus.OK)
    fun updateCash(@RequestBody body: MemberRequest.Cash): MemberResponse.Member {
        val result = service.updateCash(body.uuid, body.amount)

        return MemberResponse.Member(result)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun withdraw(@PathVariable id: UUID, @RequestBody body: MemberRequest.Withdraw) {
        service.withdraw(id, body.password)
    }

}