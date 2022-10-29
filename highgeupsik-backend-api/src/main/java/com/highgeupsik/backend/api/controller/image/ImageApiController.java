package com.highgeupsik.backend.api.controller.image;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.service.aws.S3Service;
import com.highgeupsik.backend.core.dto.image.UploadFileDTO;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final S3Service s3Service;

    @ApiOperation(value = "이미지 업로드")
    @PostMapping("/images")
    public ApiResult<List<UploadFileDTO>> uploadImage(List<MultipartFile> imageList) {
        return ApiUtils.success(s3Service.uploadFiles(imageList));
    }
}
