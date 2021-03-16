package com.doohong.oauth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthServerConfig(
    private val passwordEncoder: PasswordEncoder,
    private val dataSource: DataSource
) : AuthorizationServerConfigurerAdapter() {
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
//        clients.inMemory()
//                .withClient("testClientId")
//                .secret("{noop}testSecret")
//                .redirectUris("http://localhost:8081/oauth2/callback")
//                .authorizedGrantTypes("authorization_code")
//                .scopes("read", "write")
//                .accessTokenValiditySeconds(30000);
    }
}

