package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<String> getName (@RequestParam("name") final String firstname ){
        String response = "hello " + firstname;
        return ResponseEntity.ok().body(response);
    }
}
