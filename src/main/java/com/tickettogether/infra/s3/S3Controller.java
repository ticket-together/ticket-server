package com.tickettogether.infra.s3;

import com.tickettogether.global.error.exception.BaseException;
import com.tickettogether.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/file")
@RestController
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public BaseResponse fileUpload(@RequestParam("category") String category, @RequestPart("files") MultipartFile multipartFile){
        return BaseResponse.create(s3Service.uploadFileV1(category, multipartFile));
    }

}
