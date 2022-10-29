package com.highgeupsik.backend.core.model.user;

import com.highgeupsik.backend.core.model.TimeEntity;
import com.highgeupsik.backend.core.model.school.StudentCard;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserConfirm extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_confirm_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_card_id")
    private StudentCard studentCard;

    @Builder
    public UserConfirm(User user, StudentCard studentCard) {
        this.user = user;
        this.studentCard = studentCard;
    }
}
