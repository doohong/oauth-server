package com.doohong.resource.test

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping(value = ["/test"])
    fun test(): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(true)
    }
}