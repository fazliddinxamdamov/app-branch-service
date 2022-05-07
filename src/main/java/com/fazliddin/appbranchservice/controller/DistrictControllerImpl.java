package com.fazliddin.appbranchservice.controller;

import ai.ecma.appbranchservice.service.DistrictService;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.DistrictDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
@RestController
@RequiredArgsConstructor
public class DistrictControllerImpl implements DistrictController {
    private final DistrictService districtService;

    @Override
    public ApiResult<List<DistrictDTO>> getAllByRegionId(Long regionId) {
        return districtService.getAllByRegionId(regionId);
    }
}
