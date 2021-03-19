package com.doohong.oauth.user

import org.springframework.data.repository.CrudRepository

interface UserRepo: CrudRepository<User, Long> {
    fun  findByName(name: String): User?
}