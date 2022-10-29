package com.highgeupsik.backend.core.model.school;

import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import java.util.Arrays;

public enum GRADE {
    FIRST(1),
    SECOND(2),
    THIRD(3);

    private final int gradeNum;

    GRADE(int gradeNum) {
        this.gradeNum = gradeNum;
    }

    public static GRADE from(int gradeNum) {
        return Arrays.stream(values())
            .filter(grade -> grade.gradeNum == gradeNum)
            .findAny()
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.GRADE_NOT_FOUND));
    }

    public int getGradeNum() {
        return gradeNum;
    }
}
