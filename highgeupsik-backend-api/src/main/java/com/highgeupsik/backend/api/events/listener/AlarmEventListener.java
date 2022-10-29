package com.highgeupsik.backend.api.events.listener;

import com.highgeupsik.backend.api.events.AlarmEvent;
import com.highgeupsik.backend.api.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AlarmEventListener {

    private final AlarmService alarmService;

    @Async
    @TransactionalEventListener
    public void handleAlarmEvent(AlarmEvent alarmEvent) {
        alarmService.send(alarmEvent.getUserId(), alarmEvent.getMessage());
    }
}
