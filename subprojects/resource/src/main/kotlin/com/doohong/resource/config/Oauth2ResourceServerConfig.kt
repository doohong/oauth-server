package com.doohong.resource.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore


@Configuration
@EnableResourceServer
class Oauth2ResourceServerConfig : ResourceServerConfigurerAdapter() {
    @Value("\${security.oauth2.jwt.signkey}")
    private val signKey: String? = null

    @Bean
    fun tokenStore(): TokenStore? {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter? {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signKey)
        return converter
    }
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.headers().frameOptions().disable()
        http.authorizeRequests()
            .antMatchers("/test").access("#oauth2.hasScope('read')")
            .anyRequest().authenticated()
    }
}