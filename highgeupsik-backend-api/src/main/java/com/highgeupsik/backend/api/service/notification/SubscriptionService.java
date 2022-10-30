package com.highgeupsik.backend.api.service.notification;

import static com.highgeupsik.backend.core.exception.ErrorMessages.USER_NOT_FOUND;

import com.highgeupsik.backend.api.repository.notification.SubscriptionRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.notification.Subscription;
import com.highgeupsik.backend.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public void saveSubscription(Long userId, String token) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        if (!subscriptionRepository.existsByUser(user)) {
            subscriptionRepository.save(Subscription.builder().token(token).user(user).build());
        }
    }
}
