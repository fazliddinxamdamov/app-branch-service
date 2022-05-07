package com.fazliddin.appbranchservice.service;

import ai.ecma.lib.entity.Region;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 11.02.2022
 */
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public ApiResult<List<Region>> getAll() {
        return ApiResult.successResponse(regionRepository.findAll());
    }
}
