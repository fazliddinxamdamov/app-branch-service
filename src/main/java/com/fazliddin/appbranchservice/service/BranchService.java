package com.fazliddin.appbranchservice.service;

import ai.ecma.lib.entity.Branch;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchReqDto;
import ai.ecma.lib.payload.resp.BranchRespDto;
import org.springframework.data.domain.Page;

/**
 * @author Murtazayev Muhammad
 * @since 22.01.2022
 */
public interface BranchService {
    ApiResult<CustomPage<BranchRespDto>> get(Integer page, Integer size);

    ApiResult<CustomPage<BranchRespDto>> getByRegion(Integer page, Integer size, Long regionId);

    ApiResult<CustomPage<BranchRespDto>> getByDistrict(Integer page, Integer size, Long districtId);

    ApiResult<BranchRespDto> getNearly(Double lat, Double lon);

    ApiResult<BranchRespDto> get(Long id);

    ApiResult<?> create(BranchReqDto dto);

    ApiResult<?> edit(Long id, BranchReqDto dto);

    ApiResult<?> delete(Long id);

    CustomPage<BranchRespDto> makeCustomPage(Page<Branch> branches);
}
