package com.example.AuthBackend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HomeController {
    @GetMapping("/1")
    public String hello(){
        return "hello";
    }
}
