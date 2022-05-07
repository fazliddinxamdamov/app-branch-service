package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.service.RegionService;
import com.fazliddin.library.entity.Region;
import com.fazliddin.library.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionControllerImpl implements RegionController {
    private final RegionService regionService;

    @Override
    public ApiResult<List<Region>> getRegions() {
        return regionService.getAll();
    }
}
