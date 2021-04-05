package com.doohong.authorization.user.entity

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0,
    val memberId : String,
    val memberPassword : String,
    val phoneNumber: String?,
    @ElementCollection(fetch = FetchType.EAGER)
    val roles: List<String>
) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String {
        return memberPassword
    }

    override fun getUsername(): String {
        return memberId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}