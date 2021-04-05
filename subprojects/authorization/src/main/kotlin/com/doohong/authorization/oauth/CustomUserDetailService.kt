package com.doohong.authorization.oauth

import com.doohong.authorization.user.UserRepo
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepo: UserRepo,
) : UserDetailsService {
    private val detailsChecker = AccountStatusUserDetailsChecker()
    override fun loadUserByUsername(name: String): UserDetails {
        val user = userRepo.findByMemberId(name) ?: throw UsernameNotFoundException("user is not exists")
        detailsChecker.check(user)
        return user
    }
}