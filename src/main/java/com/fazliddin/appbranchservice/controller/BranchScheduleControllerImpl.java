package com.fazliddin.appbranchservice.controller;

import ai.ecma.appbranchservice.service.BranchScheduleService;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchScheduleReqDto;
import ai.ecma.lib.payload.resp.BranchScheduleRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
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
