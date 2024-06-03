package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/home")
public class MyController {

    @GetMapping(value = "/test")
    public @ResponseBody String test(){
        return "hello world";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/post-method")
    public ResponseEntity<String> postMethod(@RequestBody String str) {
        return ResponseEntity.ok(str);
    }

}
