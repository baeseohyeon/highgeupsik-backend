package com.highgeupsik.backend.api.controller.school;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.service.SchoolQueryService;

import com.highgeupsik.backend.core.dto.school.SchoolResDTO;
import com.highgeupsik.backend.core.dto.school.SchoolSearchCondition;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SchoolController {

    private final SchoolQueryService schoolQueryService;

    @GetMapping("/schools")
    public ApiResult<List<SchoolResDTO>> schools(@Valid SchoolSearchCondition condition) {
        return ApiUtils.success(schoolQueryService.findAllByRegionAndName(condition));
    }
}
