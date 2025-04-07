package com.ecommerce.interfaces.api

import com.ecommerce.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/member")
class MemberController(private val service: MemberService) {

    @GetMapping("/{uuid}")
    fun find(@PathVariable uuid: UUID) =
        service.find(uuid)
            .let { member -> MemberResponse.MemberInfo(member) }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun create(@RequestBody req: MemberRequest.Create) =
        service.create(req.name, req.email, req.password)
            .let { member -> MemberResponse.MemberInfo(member) }

    @PostMapping("/login")
    fun login(@RequestBody req: MemberRequest.Login) =
        service.find(req.email, req.password)
            .let { member -> MemberResponse.MemberInfo(member) }

    @PutMapping("/{uuid}/name")
    fun changeName(@PathVariable uuid: UUID, @RequestBody req: MemberRequest.Name) =
        service.changeName(uuid, req.name)
            .let { member -> MemberResponse.MemberInfo(member) }

    @PostMapping("/{uuid}/cash")
    fun chargeCash(@PathVariable uuid: UUID, @RequestBody req: MemberRequest.ChargeCash) =
        service.chargeCash(uuid, req.amount)
            .let { member -> MemberResponse.MemberInfo(member) }

    @PatchMapping("/{uuid}/cash")
    fun useUse(@PathVariable uuid: UUID, @RequestBody req: MemberRequest.UseCash) =
        service.useCash(uuid, req.amount)
            .let { member -> MemberResponse.MemberInfo(member) }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun withdraw(@PathVariable uuid: UUID, @RequestBody req: MemberRequest.Delete) =
        service.withdraw(uuid, req.password)

}
