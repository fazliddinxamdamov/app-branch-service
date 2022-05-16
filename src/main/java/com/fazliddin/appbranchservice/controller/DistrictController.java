package com.fazliddin.appbranchservice.controller;

import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.DistrictDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(DistrictController.DISTRICT_CONTROLLER)
public interface DistrictController {
    String DISTRICT_CONTROLLER = AppConstant.BASE_PATH + "/district";

    @GetMapping("/region/{regionId}")
    ApiResult<List<DistrictDTO>> getAllByRegionId(@PathVariable Long regionId);

}
