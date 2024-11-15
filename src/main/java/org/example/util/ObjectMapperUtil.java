package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class ObjectMapperUtil {
    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();

}
