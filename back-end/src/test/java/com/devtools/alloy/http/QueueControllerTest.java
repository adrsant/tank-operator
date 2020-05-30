package com.devtools.alloy.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devtools.alloy.http.handlers.CustomExceptionHandler;
import com.devtools.helper.TestHelper;
import com.devtools.helper.TestJsonHelper;
import com.devtools.interactions.queue.QueueSearch;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class QueueControllerTest {

  private static final String CONTEXT_ROOT = "/api/v1/queues";
  private MockMvc mockMvc;
  @Mock
  private QueueSearch interaction;
  @InjectMocks
  private QueueController controller;

  @Before
  public void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new CustomExceptionHandler())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

  @Test
  public void should_list_queues_by_name() throws Exception {
    String queueName = "TEST";
    var queues = new PageImpl(List.of(TestHelper.givenQueueCreated().toQueueDetails()));

    given(interaction.search(eq(queueName), any(Pageable.class))).willReturn(queues);

    mockMvc
        .perform(
            get(CONTEXT_ROOT)
                .param("name", queueName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            content()
                .json(
                    TestJsonHelper.getJsonQueueDetailsResponse(),
                    true))
        .andReturn();
  }

  @Test
  public void should_get_queue_details() throws Exception {
    var queueId = UUID.randomUUID();
    var queue = TestHelper.givenQueueCreated().toQueueDetails();

    given(interaction.search(queueId)).willReturn(queue);

    mockMvc
        .perform(
            get(CONTEXT_ROOT + "/{queueId}", queueId.toString()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            content()
                .json(
                    TestJsonHelper.getJsonQueueDetailsResponse(),
                    true))
        .andReturn();
  }
}
