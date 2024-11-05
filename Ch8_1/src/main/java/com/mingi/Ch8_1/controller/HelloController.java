package com.mingi.Ch8_1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// 8.1
    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
    
    @GetMapping("/ciao")
    public String ciao() {
        return "Ciao!";
    }
    
    @GetMapping("/hola")
    public String hola() {
        return "Hola!";
    }
    
    // 8.2
    @PostMapping("/a")
    public String postEndpointA() {
        return "Works!!";
    }
    
    @GetMapping("/a")
    public String getEndpointA() {
        return "Works!!";
    }
    
    @GetMapping("/a/b")
    public String postEndpointB() {
        return "Works!!";
    }
    
    @GetMapping("/a/b/c")
    public String getEndpointC() {
        return "Works!!";
    }
    
    
    // 8 Final
    @GetMapping("/admin/home")
    public String adminHome() {
        return "Welcome to Home, Admin";
    }

    @GetMapping("/admin/home/room")
    public String adminHomeRome() {
        return "Welcome to home room, Admin";
    }

    @GetMapping("/manager/home")
    public String managerHome() {
        return "Welcome to home, Manager";
    }

    @GetMapping("/manager/home/room")
    public String managerHomeRome() {
        return "Welcome to home room, Manager";
    }

    @PostMapping("/manager/home/room")
    public String postManagerHomeRome() {
        return "Welcome to home room, Manager";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "Welcome to home, User";
    }

    @GetMapping("/public/home")
    public String publicHome() {
        return "Welcome to home, Public";
    }
}