package com.highgeupsik.backend.api.service.user;

import com.highgeupsik.backend.api.repository.user.UserConfirmRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.api.service.MailService;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final UserConfirmRepository userConfirmRepository;
    private final MailService mailService;

    public void acceptUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
            ErrorMessages.USER_NOT_FOUND));
        user.updateRoleUser();
        userConfirmRepository.deleteByUserId(userId);
        mailService.sendEmail(user.getUsername(), user.getEmail(), true);
    }

    public void rejectUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
            ErrorMessages.USER_NOT_FOUND));
        userConfirmRepository.deleteByUserId(userId);
        mailService.sendEmail(user.getUsername(), user.getEmail(), false);
    }
}
