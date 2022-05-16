package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.service.BranchScheduleService;
import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.CustomPage;
import com.fazliddin.library.payload.req.BranchScheduleReqDto;
import com.fazliddin.library.payload.resp.BranchScheduleRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BranchScheduleControllerImpl implements BranchScheduleController {
    private final BranchScheduleService branchScheduleService;

    @Override
    public ApiResult<CustomPage<BranchScheduleRespDto>> get(Integer page, Integer size) {
        return branchScheduleService.get(page, size);
    }

    @Override
    public ApiResult<List<BranchScheduleRespDto>> getByBranch(Long branchId) {
        return branchScheduleService.getByBranch(branchId);
    }

    @Override
    public ApiResult<BranchScheduleRespDto> get(Long id) {
        return branchScheduleService.get(id);
    }

    @Override
    public ApiResult<?> create(List<BranchScheduleReqDto> dto) {
        return branchScheduleService.create(dto);
    }

    @Override
    public ApiResult<?> edit(Long id, BranchScheduleReqDto dto) {
        return branchScheduleService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return branchScheduleService.delete(id);
    }
}
