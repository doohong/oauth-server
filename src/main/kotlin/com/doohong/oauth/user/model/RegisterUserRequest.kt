package com.doohong.oauth.user.model

data class RegisterUserRequest(
    val memberId : String,
    val memberPassword : String,
    val phoneNumber: String?
)
