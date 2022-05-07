package com.fazliddin.appbranchservice.controller;

import com.fazliddin.appbranchservice.payload.ResponseFileDto;
import com.fazliddin.appbranchservice.service.AttachmentService;
import com.fazliddin.library.entity.Attachment;
import com.fazliddin.library.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController {

    private final AttachmentService attachmentService;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.upload(request);
    }

    @Override
    public void download(Long id, HttpServletResponse response) {
        attachmentService.download(id, response);
    }

    @Override
    public ApiResult<List<ResponseFileDto>> getListFiles() {
        return attachmentService.getListFiles();
    }

    @Override
    public ApiResult<Attachment> get(Long id) {
        return attachmentService.get(id);
    }
}
