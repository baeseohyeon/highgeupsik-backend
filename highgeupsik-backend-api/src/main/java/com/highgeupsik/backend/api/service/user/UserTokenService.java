package com.highgeupsik.backend.api.service.user;

import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.dto.user.UserTokenDTO;
import com.highgeupsik.backend.api.jwt.JwtTokenProvider;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserTokenService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserTokenDTO getTokenDTO(UserTokenDTO userTokenDTO) {
        String refreshToken = userTokenDTO.getRefreshToken();
        jwtTokenProvider.validateToken(refreshToken);
        String newRefreshToken = jwtTokenProvider.createRefreshToken();
        User user = userRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        user.updateRefreshToken(newRefreshToken);
        return new UserTokenDTO(jwtTokenProvider.createAccessToken(user.getId(), user.getRole().toString()),
            newRefreshToken);
    }
}
