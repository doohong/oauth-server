package com.doohong.oauth.user

import com.doohong.oauth.user.model.RegisterUserRequest
import com.doohong.oauth.user.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class UserService(
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerUser(@RequestBody req: RegisterUserRequest): Boolean {
        userRepo.save(User(memberId = req.memberId, memberPassword = passwordEncoder.encode(req.memberPassword), phoneNumber = req.phoneNumber, roles = listOf("admin")))
        return true
    }
}