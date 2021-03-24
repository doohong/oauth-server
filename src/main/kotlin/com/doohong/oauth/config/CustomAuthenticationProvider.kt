package com.doohong.oauth.config

import com.doohong.oauth.user.UserRepo
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import org.springframework.security.authentication.BadCredentialsException

import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val passwordEncoder: PasswordEncoder,
    private val userRepo: UserRepo
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val name: String = authentication.name
        val password: String = authentication.credentials.toString()
        val user = userRepo.findByMemberId(name) ?: throw UsernameNotFoundException("user is not exists")
        if (!passwordEncoder.matches(
                password,
                user.getPassword()
            )
        ) throw BadCredentialsException("password is not valid")
        return UsernamePasswordAuthenticationToken(name, password, user.getAuthorities())
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication ==
                UsernamePasswordAuthenticationToken::class.java
    }
}