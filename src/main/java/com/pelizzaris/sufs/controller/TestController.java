package com.pelizzaris.sufs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Testando o endpoint /test";
    }
}
