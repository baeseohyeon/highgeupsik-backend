package com.highgeupsik.backend.api.service.user;

import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.dto.user.UserResDTO;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public UserResDTO findById(Long userId) {
        return new UserResDTO(userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND)));
    }
}
