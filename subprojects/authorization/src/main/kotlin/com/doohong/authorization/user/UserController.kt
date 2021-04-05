package com.doohong.authorization.user

import com.doohong.authorization.user.model.RegisterUserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping(value = ["/singUp"])
    fun registerUser(@RequestBody req: RegisterUserRequest): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(userService.registerUser(req))
    }
}