package com.fazliddin.appbranchservice.service;

import com.fazliddin.appbranchservice.common.MessageService;
import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.appbranchservice.mapper.BranchScheduleMapper;
import com.fazliddin.library.entity.Branch;
import com.fazliddin.library.entity.BranchSchedule;
import com.fazliddin.library.enums.WeekdaysNameEnum;
import com.fazliddin.library.payload.ApiResult;
import com.fazliddin.library.payload.CustomPage;
import com.fazliddin.library.payload.req.BranchScheduleReqDto;
import com.fazliddin.library.payload.resp.BranchScheduleRespDto;
import com.fazliddin.library.repository.BranchRepository;
import com.fazliddin.library.repository.BranchScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BranchScheduleServiceImpl implements BranchScheduleService {
    private final BranchScheduleRepository scheduleRepository;
    private final BranchRepository branchRepository;
    private final BranchScheduleMapper scheduleMapper;

    @Override
    public ApiResult<CustomPage<BranchScheduleRespDto>> get(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BranchSchedule> schedulePage = scheduleRepository.findAll(pageRequest);
        return ApiResult.successResponse(makeCustomPage(schedulePage));
    }

    @Override
    public ApiResult<List<BranchScheduleRespDto>> getByBranch(Long branchId) {
        List<BranchSchedule> schedules = scheduleRepository.findAllByBranchId(branchId);
        return ApiResult.successResponse(schedules.stream().map((BranchSchedule schedule) -> scheduleMapper.toBranchScheduleRespDto(schedule)).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<BranchScheduleRespDto> get(Long id) {
        BranchSchedule branchSchedule = scheduleRepository.findById(id).orElseThrow(() -> RestException.notFound("BRANCH_SCHEDULE"));
        return ApiResult.successResponse(scheduleMapper.toBranchScheduleRespDto(branchSchedule));
    }

    @Override
    public ApiResult<?> create(List<BranchScheduleReqDto> dto) {
        List<BranchScheduleRespDto> response = new ArrayList<>();
        for (BranchScheduleReqDto reqDto : dto) {
            Branch branch = branchRepository.findById(reqDto.getBranchId()).orElseThrow(() -> RestException.notFound("BRANCH"));
            try {
                WeekdaysNameEnum weekday = WeekdaysNameEnum.valueOf(reqDto.getWeekDay());
                BranchSchedule branchSchedule = scheduleRepository.save(new BranchSchedule(weekday, Time.valueOf(reqDto.getStartTime()), Time.valueOf(reqDto.getEndTime()), branch));
                response.add(scheduleMapper.toBranchScheduleRespDto(branchSchedule));
            } catch (Exception e) {
                throw RestException.restThrow(MessageService.getMessage("WEEKDAY_COULD_NOT_PARSE"), HttpStatus.BAD_REQUEST);
            }
        }
        return ApiResult.successResponse(response);
    }

    @Override
    public ApiResult<?> edit(Long id, BranchScheduleReqDto dto) {
        BranchSchedule branchSchedule = scheduleRepository.findById(id).orElseThrow(() -> RestException.notFound("BRANCH_SCHEDULE"));
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> RestException.notFound("BRANCH"));
        try {
            WeekdaysNameEnum weekday = WeekdaysNameEnum.valueOf(dto.getWeekDay());
            branchSchedule.setBranch(branch);
            branchSchedule.setEndTime(Time.valueOf(dto.getEndTime()));
            branchSchedule.setStartTime(Time.valueOf(dto.getStartTime()));
            branchSchedule.setWeekdaysNameEnum(weekday);
            branchSchedule = scheduleRepository.save(branchSchedule);
            return ApiResult.successResponse(scheduleMapper.toBranchScheduleRespDto(branchSchedule));
        } catch (Exception e) {
            throw RestException.restThrow(MessageService.getMessage("WEEKDAY_COULD_NOT_PARSE"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ApiResult<?> delete(Long id) {
        try {
            scheduleRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.successDelete("BRANCH_SCHEDULE"));
        } catch (Exception e) {
            throw RestException.notFound("BRANCH_SCHEDULE");
        }
    }

    @Override
    public CustomPage<BranchScheduleRespDto> makeCustomPage(Page<BranchSchedule> schedules) {
        return new CustomPage<>(
                schedules.getContent().stream().map(scheduleMapper::toBranchScheduleRespDto).collect(Collectors.toList()),
                schedules.getNumberOfElements(),
                schedules.getNumber(),
                schedules.getTotalElements(),
                schedules.getTotalPages(),
                schedules.getSize()
        );
    }
}
