package com.fazliddin.appbranchservice.service;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.DistrictDTO;

import java.util.List;

/**
 * This interface not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
public interface DistrictService {
    ApiResult<List<DistrictDTO>> getAllByRegionId(Long regionId);

}
