package com.highgeupsik.backend.push.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FCMInitializer {

    @Value("${firebase.jsonpath}")
    private String FIREBASE_CONFIG_PATH;

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource(FIREBASE_CONFIG_PATH);
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
            FirebaseApp.initializeApp(options);
            log.info("Firebase application has been initialized");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
