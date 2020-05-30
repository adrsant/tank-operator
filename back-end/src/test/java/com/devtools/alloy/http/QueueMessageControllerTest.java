package com.devtools.alloy.http;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(QueueController.class)
public class QueueMessageControllerTest {

  private static final Long TENANT_ID = 1L;
  private static final String CONTEXT_ROOT = "/api/v1/dlqs";
  @Autowired private MockMvc mockMvc;
}
