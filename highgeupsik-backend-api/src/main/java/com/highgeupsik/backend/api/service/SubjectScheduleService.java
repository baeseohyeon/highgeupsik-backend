package com.highgeupsik.backend.api.service;

import com.baechu.cache.MyCacheStore;
import com.highgeupsik.backend.api.controller.subjectschedule.neis.Schedule;
import com.highgeupsik.backend.api.controller.subjectschedule.neis.TimetableRequestCondition;
import com.highgeupsik.backend.core.dto.subjectschedule.SubjectDTO;
import com.highgeupsik.backend.core.dto.subjectschedule.SubjectScheduleDTO;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.api.utils.MyDateUtils;
import com.highgeupsik.backend.api.utils.OpenApiRequestUtils;
import com.highgeupsik.backend.api.utils.UrlGenerator;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.user.User;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubjectScheduleService {

    private final UserRepository userRepository;
    private final UrlGenerator urlGenerator;
    private final MyCacheStore<String, SubjectScheduleDTO> cacheStore;

    public SubjectScheduleDTO findScheduleByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
            ErrorMessages.USER_NOT_FOUND));
        TimetableRequestCondition condition = new TimetableRequestCondition(user.getStudentCard());
        return findScheduleByCustomCache(condition);
    }

    private SubjectScheduleDTO findScheduleByCustomCache(TimetableRequestCondition condition) {
        SubjectScheduleDTO scheduleDTO = cacheStore.get(condition.getKey());
        if (Objects.isNull(scheduleDTO)) {
            String[] dates = MyDateUtils.getWeekDates();
            scheduleDTO  = new SubjectScheduleDTO(
                getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[1])),
                getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[2])),
                getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[3])),
                getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[4])),
                getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[5]))
            );
            cacheStore.put(condition.getKey(), scheduleDTO);
        }
        return scheduleDTO;
    }

    @Cacheable(value = "timetable")
    public SubjectScheduleDTO findScheduleBySpringCache(TimetableRequestCondition condition) {
        String[] dates = MyDateUtils.getWeekDates();
        return new SubjectScheduleDTO(
          getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[1])),
          getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[2])),
          getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[3])),
          getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[4])),
          getSchedules(urlGenerator.getScheduleRequestUrl(condition, dates[5]))
        );
    }

    private List<SubjectDTO> getSchedules(String url) {
        return OpenApiRequestUtils.getRequest(url, Schedule.class).getTimeTable()
            .stream()
            .map((t) -> new SubjectDTO(t.getPERIO(), t.getITRT_CNTNT(), t.getCLRM_NM()))
            .collect(Collectors.toList());
    }
}
