package com.highgeupsik.backend.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmEvent {

    private Long userId;
    private String message;
}
