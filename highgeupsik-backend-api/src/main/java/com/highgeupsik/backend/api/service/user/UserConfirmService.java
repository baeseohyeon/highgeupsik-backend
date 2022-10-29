package com.highgeupsik.backend.api.service.user;

import com.highgeupsik.backend.api.repository.user.UserConfirmRepository;
import com.highgeupsik.backend.core.dto.admin.UserConfirmDTO;
import com.highgeupsik.backend.api.utils.PagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserConfirmService {

    private final UserConfirmRepository userConfirmRepository;
    private static final int USER_CONFIRM_COUNT = 20;

    public Page<UserConfirmDTO> findAll(Integer pageNum) {
        return userConfirmRepository.findAll(PagingUtils.orderByCreatedDateASC(pageNum, USER_CONFIRM_COUNT))
            .map(UserConfirmDTO::new);
    }
}
