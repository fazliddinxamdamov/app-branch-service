package com.fazliddin.appbranchservice.service;

import ai.ecma.appbranchservice.mapper.DistrictMapper;
import ai.ecma.lib.entity.District;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.DistrictDTO;
import ai.ecma.lib.repository.DistrictRepository;
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
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    @Override
    public ApiResult<List<DistrictDTO>> getAllByRegionId(Long regionId) {
        List<District> districtList = districtRepository.findAllByRegionId(regionId);
        return ApiResult.successResponse(districtMapper.toDTOList(districtList));
    }
}
