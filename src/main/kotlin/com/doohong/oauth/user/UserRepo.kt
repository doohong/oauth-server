package com.doohong.oauth.user

import com.doohong.oauth.user.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepo: CrudRepository<User, Long> {
    fun  findByName(name: String): User?
}