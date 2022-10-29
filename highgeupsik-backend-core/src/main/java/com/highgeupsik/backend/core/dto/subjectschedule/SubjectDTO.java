package com.highgeupsik.backend.core.dto.subjectschedule;

import lombok.Getter;

@Getter
public class SubjectDTO {

    String perio;
    String name;
    String lectureRoom;

    public SubjectDTO(String perio, String name, String lectureRoom) {
        this.perio = perio;
        this.name = name;
        this.lectureRoom = lectureRoom;
    }
}
