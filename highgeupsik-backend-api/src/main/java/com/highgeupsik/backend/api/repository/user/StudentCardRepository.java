package com.highgeupsik.backend.api.repository.user;

import com.highgeupsik.backend.core.model.school.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {

}
