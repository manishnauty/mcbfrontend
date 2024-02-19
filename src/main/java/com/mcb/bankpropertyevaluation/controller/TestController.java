package com.mcb.bankpropertyevaluation.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/secured")
public class TestController {


    @GetMapping("/status")
    public String getStatus(){
        return "application is up and running";
    }
}
