package com.doohong.authorization.oauth.model

data class OAuthToken(
        val access_token: String,
        val token_type: String,
        val refresh_token: String,
        val expires_in: Long,
        val scope: String
)
