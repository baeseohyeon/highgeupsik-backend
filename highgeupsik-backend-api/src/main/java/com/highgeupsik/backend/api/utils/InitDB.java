package com.highgeupsik.backend.api.utils;

import com.highgeupsik.backend.api.controller.school.neis.SchoolInfo;
import com.highgeupsik.backend.api.repository.school.SchoolRepository;
import com.highgeupsik.backend.core.model.school.School;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit();
    }

    @RequiredArgsConstructor
    @Transactional
    @Component
    static class InitService {

        private final SchoolRepository schoolRepository;
        private final UrlGenerator urlGenerator;

        public void dbInit() {
            List<School> schools = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                schools.addAll(OpenApiRequestUtils.getRequest(urlGenerator.getSchoolRequestUrl(i), SchoolInfo.class)
                    .getSchoolInfoRes()
                    .stream()
                    .map((s) -> new School(s.getSCHUL_NM(), s.getSD_SCHUL_CODE(), s.getATPT_OFCDC_SC_CODE(),
                        s.getLCTN_SC_NM(), s.getHMPG_ADRES()))
                    .collect(Collectors.toList()));
            }
            schoolRepository.saveAll(schools);
        }
    }
}
