package com.highgeupsik.backend.api.controller.subjectschedule;


import static com.highgeupsik.backend.api.controller.ApiUtils.success;

import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.SubjectScheduleService;
import com.highgeupsik.backend.api.controller.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubjectScheduleController {

    private final SubjectScheduleService subjectScheduleService;

    @GetMapping("/subject-schedules")
    public ApiResult timetables(@LoginUser Long userId){
        return success(subjectScheduleService.findScheduleByUserId(userId));
    }
}
