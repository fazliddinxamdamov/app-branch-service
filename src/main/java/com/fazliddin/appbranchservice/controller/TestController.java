package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.utils.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fazliddin Xamdamov
 * @date 25.04.2022  08:59
 * @project app-fast-food
 */

@RestController
@RequestMapping(TestController.PATH)
public interface TestController {

   String PATH = AppConstants.BASE_PATH;

    @GetMapping("/test")
    public String testController();
}
