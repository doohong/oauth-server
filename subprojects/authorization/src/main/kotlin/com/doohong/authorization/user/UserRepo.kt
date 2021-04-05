package com.doohong.authorization.user

import com.doohong.authorization.user.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: CrudRepository<User, Long> {
    fun  findByMemberId(memberId: String): User?
}