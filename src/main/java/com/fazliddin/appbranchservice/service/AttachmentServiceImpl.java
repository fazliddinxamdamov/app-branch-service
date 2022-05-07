package com.fazliddin.appbranchservice.service;

import ai.ecma.appbranchservice.common.MessageService;
import ai.ecma.appbranchservice.exception.RestException;
import ai.ecma.appbranchservice.payload.ResponseFileDto;
import ai.ecma.lib.entity.Attachment;
import ai.ecma.lib.entity.AttachmentContent;
import ai.ecma.lib.payload.ApiResult;
import ai.ecma.lib.repository.AttachmentContentRepository;
import ai.ecma.lib.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository contentRepository;


    @Override
    public ApiResult<Attachment> get(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(
                () -> RestException.notFound(MessageService.notFound("ATTACHMENT")));
        return ApiResult.successResponse(attachment);
    }


    @Override
    @SneakyThrows
    public ApiResult<?> upload(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = Attachment.builder()
                .originalName(file.getOriginalFilename())
                .name(file.getName())
                .size(file.getSize())
                .contentType(file.getContentType()).build();
        Attachment save = attachmentRepository.save(attachment);

        AttachmentContent content = AttachmentContent.builder()
                .attachment(save)
                .bytes(file.getBytes()).build();
        contentRepository.save(content);

        ResponseFileDto responseFileDto = new ResponseFileDto(
                attachment.getName(),
                attachment.getOriginalName(),
                path(attachment),
                attachment.getContentType(),
                attachment.getSize()
        );
        return ApiResult.successResponse(responseFileDto);
    }

    @Override
    public void download(Long id, HttpServletResponse response) {
        Optional<Attachment> file = attachmentRepository.findById(id);
        if (file.isPresent()) {
            Attachment attachment = file.get();
            Optional<AttachmentContent> attachmentContent = contentRepository.findByAttachmentId(id);
            if (attachmentContent.isPresent()) {
                AttachmentContent content = attachmentContent.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                try {
                    FileCopyUtils.copy(content.getBytes(), response.getOutputStream());
                } catch (Exception e) {
                    throw RestException.badRequest();
                }
            }
        }
    }

    @Override
    public ApiResult<List<ResponseFileDto>> getListFiles() {
        List<ResponseFileDto> files = getAllFiles().map(attachment -> new ResponseFileDto(
                attachment.getName(),
                attachment.getOriginalName(),
                path(attachment),
                attachment.getContentType(),
                attachment.getSize())).collect(Collectors.toList());

        return ApiResult.successResponse(files);
    }


    private Stream<Attachment> getAllFiles() {
        return attachmentRepository.findAll().stream();
    }

    private String path(Attachment attachment) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().host("localhost").port(8080)
                .path("/api/branch/v1/attachment/download/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();
    }
}
