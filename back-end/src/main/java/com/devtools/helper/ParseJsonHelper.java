package com.devtools.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ParseJsonHelper {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    MAPPER.registerModule(new JavaTimeModule());
    MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public <T> T toObject(String content, Class<T> valueType) {
    try {
      return MAPPER.readValue(content, valueType);
    } catch (Exception e) {
      log.error(
          "PARSE ERROR TO OBJECT, CLASS {}, content {} : msg {}",
          valueType,
          content,
          e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public String toJson(Object content) {
    try {
      return MAPPER.writeValueAsString(content);
    } catch (Exception e) {
      log.error(
          "PARSE ERROR TO JSON, CLASS {}, content {} : msg {}", content.getClass(), e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
