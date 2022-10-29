package com.highgeupsik.backend.core.dto.user;

import com.highgeupsik.backend.core.dto.school.SchoolResDTO;
import com.highgeupsik.backend.core.model.user.Role;
import com.highgeupsik.backend.core.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResDTO {

    private Long id;
    private String email;
    private String username;
    private Role role;
    private int grade;
    private int classNum;
    private String studentCardImage;
    private SchoolResDTO schoolResDTO;

    public UserResDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        username = user.getUsername();
        role = user.getRole();
        grade = user.getStudentCard().getGrade().getGradeNum();
        classNum = user.getStudentCard().getClassNum();
        studentCardImage = user.getStudentCard().getStudentCardImage();
        schoolResDTO = new SchoolResDTO(user.getStudentCard().getSchool());
    }
}
