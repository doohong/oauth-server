package com.doohong.oauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import java.lang.Exception


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Bean
    fun noOpPasswordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}pass")
                .roles("USER")
    }

    override fun configure(security: HttpSecurity) {
        security
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
                .and()
                .formLogin().and()
                .httpBasic()
    }
}