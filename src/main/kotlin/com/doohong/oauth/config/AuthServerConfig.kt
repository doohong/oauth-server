package com.doohong.oauth.config

import com.doohong.oauth.oauth.CustomUserDetailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthServerConfig(
    private val passwordEncoder: PasswordEncoder,
    private val dataSource: DataSource,
    private val customUserDetailService: CustomUserDetailService
) : AuthorizationServerConfigurerAdapter() {
    @Value("\${security.oauth2.jwt.signkey}")
    private lateinit var signKey: String
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(customUserDetailService)
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signKey)
        return converter
    }
}

