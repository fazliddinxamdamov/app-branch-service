package com.fazliddin.appbranchservice.controller;

import ai.ecma.appbranchservice.service.BranchService;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchReqDto;
import ai.ecma.lib.payload.resp.BranchRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Murtazayev Muhammad
 * @since 22.01.2022
 */
@RestController
@RequiredArgsConstructor
public class BranchControllerImpl implements BranchController {
    private final BranchService branchService;

    @Override
    public ApiResult<CustomPage<BranchRespDto>> get(Integer page, Integer size) {
        return branchService.get(page, size);
    }

    @Override
    public ApiResult<CustomPage<BranchRespDto>> getByRegion(Integer page, Integer size, Long regionId) {
        return branchService.getByRegion(page, size, regionId);
    }

    @Override
    public ApiResult<CustomPage<BranchRespDto>> getByDistrict(Integer page, Integer size, Long districtId) {
        return branchService.getByDistrict(page, size, districtId);
    }

    @Override
    public ApiResult<BranchRespDto> getNearly(Double lat, Double lon) {
        return branchService.getNearly(lat, lon);
    }

    @Override
    public ApiResult<BranchRespDto> get(Long id) {
        return branchService.get(id);
    }

    @Override
    public ApiResult<?> create(BranchReqDto dto) {
        return branchService.create(dto);
    }

    @Override
    public ApiResult<?> edit(Long id, BranchReqDto dto) {
        return branchService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return branchService.delete(id);
    }
}
