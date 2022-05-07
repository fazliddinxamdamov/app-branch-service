package com.fazliddin.appbranchservice.service;

import ai.ecma.lib.entity.BranchSchedule;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchScheduleReqDto;
import ai.ecma.lib.payload.resp.BranchScheduleRespDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
public interface BranchScheduleService {

    ApiResult<CustomPage<BranchScheduleRespDto>> get(Integer page, Integer size);

    ApiResult<List<BranchScheduleRespDto>> getByBranch(Long branchId);

    ApiResult<BranchScheduleRespDto> get(Long id);

    ApiResult<?> create(List<BranchScheduleReqDto> dto);

    ApiResult<?> edit(Long id, BranchScheduleReqDto dto);

    ApiResult<?> delete(Long id);

    CustomPage<BranchScheduleRespDto> makeCustomPage(Page<BranchSchedule> schedules);
}
