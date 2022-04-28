package com.fazliddin.appbranchservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fazliddin Xamdamov
 * @date 25.04.2022  08:59
 * @project app-fast-food
 */

@RestController

public class TestController {

    @GetMapping("/api/auth/branch")
    public String testController(){
        return "Branch service test controller";
    }
}
