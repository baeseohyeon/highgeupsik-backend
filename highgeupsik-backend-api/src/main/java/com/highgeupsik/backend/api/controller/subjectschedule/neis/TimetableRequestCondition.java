package com.highgeupsik.backend.api.controller.subjectschedule.neis;

import com.highgeupsik.backend.core.model.school.StudentCard;
import lombok.Getter;

@Getter
public class TimetableRequestCondition {

    String regionCode;
    String schoolCode;
    int grade;
    int classNm;

    public TimetableRequestCondition(StudentCard studentCard){
        regionCode = studentCard.getSchool().getRegionCode();
        schoolCode = studentCard.getSchool().getSchoolCode();
        grade = studentCard.getGrade().getGradeNum();
        classNm = studentCard.getClassNum();
    }

    public String getKey(){
        return regionCode+schoolCode+grade+classNm;
    }
}
