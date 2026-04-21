package com.upiiz.practica2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/mascotas")
public class MascotasController {

    @GetMapping("/login")
    public String auth() {
        return "mascotas/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "mascotas/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "mascotas/auth/forgot-password";
    }

    @GetMapping("/index")
    public String index() {
        return "mascotas/index";
    }
    
    
    

}
