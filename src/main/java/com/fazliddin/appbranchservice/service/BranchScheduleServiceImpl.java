package com.fazliddin.appbranchservice.service;

import ai.ecma.appbranchservice.common.MessageService;
import ai.ecma.appbranchservice.exception.RestException;
import ai.ecma.appbranchservice.mapper.BranchScheduleMapper;
import ai.ecma.lib.entity.Branch;
import ai.ecma.lib.entity.BranchSchedule;
import ai.ecma.lib.enums.WeekdaysNameEnum;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchScheduleReqDto;
import ai.ecma.lib.payload.resp.BranchScheduleRespDto;
import ai.ecma.lib.repository.BranchRepository;
import ai.ecma.lib.repository.BranchScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Murtazayev Muhammad
 * @since 23.01.2022
 */
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
        return ApiResult.successResponse(schedules.stream().map(scheduleMapper::toBranchScheduleRespDto).collect(Collectors.toList()));
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
