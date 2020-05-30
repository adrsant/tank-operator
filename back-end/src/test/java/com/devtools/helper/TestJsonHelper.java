package com.devtools.helper;

import com.google.common.base.Charsets;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StreamUtils;

@UtilityClass
public class TestJsonHelper {

  @SneakyThrows(Exception.class)
  public String getContent(String pathFile) {
    return StreamUtils.copyToString(TestHelper.class.getResourceAsStream(pathFile), Charsets.UTF_8);
  }

  @SneakyThrows(Exception.class)
  public String getJsonQueueDetailsResponse() {
    String pathFile = "/json-helper/response/get-queue-details.json";
    return getContent(pathFile);
  }
}
