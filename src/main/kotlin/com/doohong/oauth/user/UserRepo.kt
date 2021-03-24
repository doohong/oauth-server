package com.doohong.oauth.user

import com.doohong.oauth.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: CrudRepository<User, Long> {
    fun  findByMemberId(memberId: String): User?
}