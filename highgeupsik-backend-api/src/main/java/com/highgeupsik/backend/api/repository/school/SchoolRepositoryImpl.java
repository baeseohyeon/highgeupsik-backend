package com.highgeupsik.backend.api.repository.school;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.highgeupsik.backend.core.dto.school.SchoolSearchCondition;
import com.highgeupsik.backend.core.model.school.QSchool;
import com.highgeupsik.backend.core.model.school.Region;
import com.highgeupsik.backend.core.model.school.School;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchoolRepositoryImpl implements SchoolRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<School> findAllByRegionAndName(SchoolSearchCondition condition) {
        return query.selectFrom(QSchool.school)
            .where(regionEq(condition.getRegion()),
                nameContains(condition.getKeyword()))
            .orderBy(QSchool.school.name.asc())
            .fetch();
    }

    private BooleanExpression regionEq(Region region) {
        return isEmpty(region) ? null : QSchool.school.region.eq(region);
    }

    private BooleanExpression nameContains(String name) {
        return isEmpty(name) ? null : QSchool.school.name.contains(name);
    }
}
