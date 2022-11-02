package com.highgeupsik.backend.push.service;

import com.highgeupsik.backend.core.events.JoinEvent;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final String subject = "하이급식 회원가입 안내 메일입니다.";

    @Async
    public void sendEmail(JoinEvent event) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            String body = getBody(event.isJoined(), event.getUsername());
            messageHelper.setSubject(subject);
            messageHelper.setTo(event.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("messaging exception occurred: {}", e.getMessage(), e);
        }
    }

    public String getBody(boolean isJoined, String username) {
        StringBuilder body = new StringBuilder();
        body.append("<html><body><p><strong>");
        if (isJoined) {
            body.append(username).append("님 하이급식에 오신것을 환영합니다!<br/>")
                .append("하단링크 접속 및 로그아웃 후 다시 로그인해주세요.</strong></p><br/>");
        } else {
            body.append(username).append("님 가입이 거부되었습니다.<br/>")
                .append("하단링크 접속 후 학생증을 다시 제출해 주세요.</strong></p><br/>");
        }
        body.append("<a href = 'https://higk.o-r.kr/'>하이급식 접속하기</a></body></html>");
        return body.toString();
    }
}
