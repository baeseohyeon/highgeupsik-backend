package com.highgeupsik.backend.api.service.user;

import com.highgeupsik.backend.api.repository.school.SchoolRepository;
import com.highgeupsik.backend.api.repository.user.StudentCardRepository;
import com.highgeupsik.backend.api.repository.user.UserConfirmRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.school.GRADE;
import com.highgeupsik.backend.core.model.school.Region;
import com.highgeupsik.backend.core.model.school.School;
import com.highgeupsik.backend.core.model.school.StudentCard;
import com.highgeupsik.backend.core.model.user.User;
import com.highgeupsik.backend.core.model.user.UserConfirm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConfirmRepository userConfirmRepository;
    private final SchoolRepository schoolRepository;
    private final StudentCardRepository studentCardRepository;

    public void updateUser(Long userId, int grade, int classNum, String studentCardImage, Region region, String schoolName) {
        School school = schoolRepository.findByRegionAndName(region, schoolName)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_NOT_FOUND));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        StudentCard studentCard = studentCardRepository
            .save(new StudentCard(school, GRADE.from(grade), classNum, studentCardImage));
        user.updateRoleGuest();
        user.setStudentCard(studentCard);
        saveUserConfirm(user);
    }

    public void saveUserConfirm(User user) {
        userConfirmRepository.save(UserConfirm.builder()
            .user(user)
            .studentCard(user.getStudentCard())
            .build());
    }
}
