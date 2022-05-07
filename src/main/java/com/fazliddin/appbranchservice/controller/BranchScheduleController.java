package com.fazliddin.appbranchservice.controller;

import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.payload.CustomPage;
import ai.ecma.lib.payload.req.BranchScheduleReqDto;
import ai.ecma.lib.payload.resp.BranchScheduleRespDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ai.ecma.appbranchservice.utils.AppConstant.*;

/**
 * @author Murtazayev Muhammad
 * @since 22.01.2022
 */
@RequestMapping(BranchScheduleController.BRANCH_SCHEDULE_CONTROLLER)
public interface BranchScheduleController {
    String BRANCH_SCHEDULE_CONTROLLER = BASE_PATH + "/branch-schedule";

    @GetMapping
    ApiResult<CustomPage<BranchScheduleRespDto>> get(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                     @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/branch/{branchId}")
    ApiResult<List<BranchScheduleRespDto>> getByBranch(@PathVariable Long branchId);

    @GetMapping("/{id}")
    ApiResult<BranchScheduleRespDto> get(@PathVariable Long id);

    @PostMapping("/create")
    ApiResult<?> create(@RequestBody @Valid List<BranchScheduleReqDto> dto);

    @PutMapping("/edit/{id}")
    ApiResult<?> edit(@PathVariable Long id, @RequestBody @Valid BranchScheduleReqDto dto);

    @DeleteMapping("/delete/{id}")
    ApiResult<?> delete(@PathVariable Long id);
}
