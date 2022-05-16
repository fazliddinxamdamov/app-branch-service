package com.fazliddin.appbranchservice.service;

import com.fazliddin.library.entity.BranchSchedule;
import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.CustomPage;
import com.fazliddin.library.payload.req.BranchScheduleReqDto;
import com.fazliddin.library.payload.resp.BranchScheduleRespDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BranchScheduleService {

    ApiResult<CustomPage<BranchScheduleRespDto>> get(Integer page, Integer size);

    ApiResult<List<BranchScheduleRespDto>> getByBranch(Long branchId);

    ApiResult<BranchScheduleRespDto> get(Long id);

    ApiResult<?> create(List<BranchScheduleReqDto> dto);

    ApiResult<?> edit(Long id, BranchScheduleReqDto dto);

    ApiResult<?> delete(Long id);

    CustomPage<BranchScheduleRespDto> makeCustomPage(Page<BranchSchedule> schedules);
}
