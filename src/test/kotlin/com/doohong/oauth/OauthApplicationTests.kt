package com.doohong.oauth

import com.doohong.oauth.user.UserRepo
import com.doohong.oauth.user.model.User
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class OauthApplicationTests(
    private val userRepo: UserRepo
){
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null
    @Test
    fun insertNewUser() {
        userRepo.save(
            User(1, "test@gmail.com", passwordEncoder!!.encode("1234"), null)
        )
    }
}
