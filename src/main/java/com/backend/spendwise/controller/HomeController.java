package com.backend.spendwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping({"/status", "/health"})
public class HomeController {

    @GetMapping
    public String HealthCheck() {
        return "Application is running successfully!!!";
    // Add methods to handle requests here
    }   
}
