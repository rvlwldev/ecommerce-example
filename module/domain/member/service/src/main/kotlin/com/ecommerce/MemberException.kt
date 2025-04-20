package com.ecommerce


class MemberException {

    class NotFound : CommonException.NotFound("Member Not Found")
    class Unauthorized : CommonException.Unauthorized("Invalid email or password")
    class InvalidPassword : CommonException.InvalidState("Invalid password")
    class InvalidEmail : CommonException.InvalidValue("Invalid Email")
    class EmptyName : CommonException.InvalidValue("Input name is empty")
    class NotEnoughCash : CommonException.InvalidState("Not Enough Cash")

}
