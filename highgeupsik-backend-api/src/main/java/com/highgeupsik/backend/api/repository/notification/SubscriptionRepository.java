package com.highgeupsik.backend.api.repository.notification;

import com.highgeupsik.backend.core.model.notification.Subscription;
import com.highgeupsik.backend.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsByUser(User user);
}
