package com.fazliddin.appbranchservice.mapper;

import com.fazliddin.library.entity.BranchSchedule;
import com.fazliddin.library.payload.resp.BranchScheduleRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchScheduleMapper {
    @Mapping(target = "branchId", source = "branch.id")
    BranchScheduleRespDto toBranchScheduleRespDto(BranchSchedule schedule);
}
