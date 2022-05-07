package com.fazliddin.appbranchservice.service;

import ai.ecma.lib.entity.Region;
import ai.ecma.lib.payload.ApiResult;

import java.util.List;

/**
 * This interface not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
public interface RegionService {
    ApiResult<List<Region>> getAll();

}
