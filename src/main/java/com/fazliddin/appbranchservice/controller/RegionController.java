package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.utils.AppConstants;
import com.fazliddin.library.entity.Region;
import com.fazliddin.library.payload.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(AppConstants.BASE_PATH + "/region")
public interface RegionController {

    @GetMapping
    ApiResult<List<Region>> getRegions();

}
