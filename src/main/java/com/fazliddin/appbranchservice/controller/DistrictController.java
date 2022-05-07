package com.fazliddin.appbranchservice.controller;

import ai.ecma.appbranchservice.utils.AppConstant;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.DistrictDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * This interface not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
@RequestMapping(DistrictController.DISTRICT_CONTROLLER)
public interface DistrictController {
    String DISTRICT_CONTROLLER = AppConstant.BASE_PATH + "/district";

    @GetMapping("/region/{regionId}")
    ApiResult<List<DistrictDTO>> getAllByRegionId(@PathVariable Long regionId);

}
