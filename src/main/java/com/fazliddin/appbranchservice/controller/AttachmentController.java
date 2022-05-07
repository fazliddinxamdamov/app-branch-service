package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.payload.ResponseFileDto;
import com.fazliddin.appbranchservice.utils.AppConstants;
import com.fazliddin.library.entity.Attachment;
import com.fazliddin.library.payload.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping(AppConstants.BASE_PATH + "/attachment")
public interface AttachmentController {

    @PostMapping("/upload")
    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    @GetMapping("/download/{id}")
    void download(@PathVariable(value = "id") Long id, HttpServletResponse response);

    @GetMapping()
    ApiResult<List<ResponseFileDto>> getListFiles();

    @GetMapping("{id}")
    ApiResult<Attachment> get(@PathVariable Long id);

}
