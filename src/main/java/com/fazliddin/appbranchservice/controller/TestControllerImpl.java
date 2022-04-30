package com.fazliddin.appbranchservice.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fazliddin Xamdamov
 * @date 30.04.2022  08:52
 * @project app-fast-food
 */

@RestController
public class TestControllerImpl implements TestController {

    @Override
    public String testController() {
        return "branch test";
    }
}
