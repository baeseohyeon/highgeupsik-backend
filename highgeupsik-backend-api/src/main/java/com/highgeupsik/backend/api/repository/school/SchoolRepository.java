package com.highgeupsik.backend.api.repository.school;

import com.highgeupsik.backend.core.model.school.Region;
import com.highgeupsik.backend.core.model.school.School;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {

    Optional<School> findByRegionAndName(Region region, String name);
}
