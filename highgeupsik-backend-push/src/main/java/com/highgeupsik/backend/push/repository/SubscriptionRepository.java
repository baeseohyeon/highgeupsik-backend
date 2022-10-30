package com.highgeupsik.backend.push.repository;

import com.highgeupsik.backend.core.model.notification.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByUserId(Long userId);
}
