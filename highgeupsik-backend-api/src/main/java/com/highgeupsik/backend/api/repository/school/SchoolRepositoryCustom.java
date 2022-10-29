package com.highgeupsik.backend.api.repository.school;

import com.highgeupsik.backend.core.dto.school.SchoolSearchCondition;
import com.highgeupsik.backend.core.model.school.School;
import java.util.List;

public interface SchoolRepositoryCustom {

    List<School> findAllByRegionAndName(SchoolSearchCondition condition);
}
