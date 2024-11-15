package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

public class ObjectMapperUtil {

    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();

}
