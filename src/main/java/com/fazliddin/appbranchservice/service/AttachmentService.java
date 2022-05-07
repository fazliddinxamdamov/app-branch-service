package com.fazliddin.appbranchservice.service;

import com.fazliddin.appbranchservice.payload.ResponseFileDto;
import com.fazliddin.library.entity.Attachment;
import com.fazliddin.library.payload.ApiResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AttachmentService {

    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    void download(Long id, HttpServletResponse response);

    ApiResult<List<ResponseFileDto>> getListFiles();

    ApiResult<Attachment> get(Long id);
}
