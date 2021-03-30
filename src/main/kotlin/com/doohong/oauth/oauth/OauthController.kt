package com.doohong.oauth.oauth

import com.doohong.oauth.oauth.model.OAuthToken
import com.google.gson.Gson
import org.apache.commons.codec.binary.Base64
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/oauth2")
class Oauth2Controller(
    private val gson: Gson,
    private val restTemplate: RestTemplate
) {
    // 나중에 실제 사용하는쪽으로 옮길예정
    @GetMapping(value = ["/callback"])
    fun callbackSocial(@RequestParam code: String?): OAuthToken? {
        val credentials = "testClientId:testSecret"
        val encodedCredentials: String = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("grant_type", "authorization_code")
        params.add("redirect_uri", "http://localhost:8081/oauth2/callback")
        val request = HttpEntity(params, headers)
        val response = restTemplate!!.postForEntity("http://localhost:8081/oauth/token", request, String::class.java)
        return if (response.statusCode == HttpStatus.OK) {
            gson.fromJson(response.body, OAuthToken::class.java)
        } else null
    }

    // 테스트용 토큰 refresh
    @GetMapping(value = ["/token/refresh"])
    fun refreshToken(@RequestParam refreshToken: String?): OAuthToken? {
        val credentials = "testClientId:testSecret"
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("refresh_token", refreshToken)
        params.add("grant_type", "refresh_token")
        val request = HttpEntity(params, headers)
        val response = restTemplate.postForEntity(
            "http://localhost:8081/oauth/token", request,
            String::class.java
        )
        return if (response.statusCode == HttpStatus.OK) {
            gson.fromJson(response.body, OAuthToken::class.java)
        } else null
    }

    @GetMapping(value = ["/secondapp/callback"]) // 1. endpoint 주소 수정
    fun callbackToSecondApp(@RequestParam code: String?): OAuthToken? {
        val credentials = "secondapp:secondsecret" // 2. credentials 정보 수정
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("grant_type", "authorization_code")
        params.add("redirect_uri", "http://localhost:8081/oauth2/secondapp/callback") // 3. redirect_uri 정보 수정
        val request = HttpEntity(params, headers)
        val response = restTemplate.postForEntity(
            "http://localhost:8081/oauth/token", request,
            String::class.java
        )
        return if (response.statusCode == HttpStatus.OK) {
            gson.fromJson(response.body, OAuthToken::class.java)
        } else null
    }

    @GetMapping(value = ["/secondapp/token/refresh"]) // 4. endpoint 주소 수정
    fun refreshTokenToSecondApp(@RequestParam refreshToken: String?): OAuthToken? {
        val credentials = "secondapp:secondsecret" // 5. credentials 정보 수정
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Authorization", "Basic $encodedCredentials")
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("refresh_token", refreshToken)
        params.add("grant_type", "refresh_token")
        val request = HttpEntity(params, headers)
        val response = restTemplate.postForEntity(
            "http://localhost:8081/oauth/token", request,
            String::class.java
        )
        return if (response.statusCode == HttpStatus.OK) {
            gson.fromJson(response.body, OAuthToken::class.java)
        } else null
    }
}