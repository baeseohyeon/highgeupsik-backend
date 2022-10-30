package com.highgeupsik.backend.push.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParsingUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parseKafkaMessage(String message, Class<T[]> clazz) {
        try {
            return mapper.readValue(message, clazz)[0];
        } catch (JsonProcessingException | IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
