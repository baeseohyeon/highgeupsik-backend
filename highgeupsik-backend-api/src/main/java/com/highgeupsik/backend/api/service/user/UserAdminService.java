package com.highgeupsik.backend.api.service.user;

import static com.highgeupsik.backend.core.exception.ErrorMessages.*;

import com.highgeupsik.backend.api.repository.user.UserConfirmRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.events.JoinEvent;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final UserConfirmRepository userConfirmRepository;
    private final ApplicationEventPublisher publisher;

    public void acceptUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        user.updateRoleUser();
        userConfirmRepository.deleteByUserId(userId);
        publisher.publishEvent(JoinEvent.ofSuccess(user.getEmail(), user.getUsername()));
    }

    public void rejectUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        userConfirmRepository.deleteByUserId(userId);
        publisher.publishEvent(JoinEvent.ofFail(user.getEmail(), user.getUsername()));
    }
}
