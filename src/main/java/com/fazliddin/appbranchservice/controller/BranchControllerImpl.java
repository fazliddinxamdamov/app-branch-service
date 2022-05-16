package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.service.BranchService;
import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.CustomPage;
import com.fazliddin.library.payload.req.BranchReqDto;
import com.fazliddin.library.payload.resp.BranchRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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
