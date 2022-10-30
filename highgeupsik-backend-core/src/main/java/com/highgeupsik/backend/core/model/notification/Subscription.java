package com.highgeupsik.backend.core.model.notification;

import com.highgeupsik.backend.core.model.TimeEntity;
import com.highgeupsik.backend.core.model.user.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Subscription extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public Subscription(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
