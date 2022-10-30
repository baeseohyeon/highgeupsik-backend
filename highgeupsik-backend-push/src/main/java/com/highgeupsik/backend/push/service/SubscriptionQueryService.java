package com.highgeupsik.backend.push.service;

import static com.highgeupsik.backend.core.exception.ErrorMessages.SUBSCRIPTION_NOT_FOUND;

import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.notification.Subscription;
import com.highgeupsik.backend.push.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription findByUserId(Long userId) {
        return subscriptionRepository.findFirstByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException(SUBSCRIPTION_NOT_FOUND));
    }
}
