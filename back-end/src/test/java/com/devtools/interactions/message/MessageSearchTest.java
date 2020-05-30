package com.devtools.interactions.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.devtools.helper.TestHelper;
import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.UUID;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageSearchTest {

  @Mock
  private MessageService messageService;
  @Mock
  private QueueService queueService;
  @InjectMocks
  private MessageSearch search;

  @Test
  public void should_search_message() {
    UUID queueId = UUID.randomUUID();
    UUID messageId = UUID.randomUUID();
    var queueCreated = TestHelper.givenQueueCreated();
    var message = TestHelper.givenMessageToConsume();

    given(queueService.getQueueAws(queueId)).willReturn(queueCreated);
    given(messageService.getMessageById(queueCreated, messageId)).willReturn(message);

    var messageDetails = search.getMessage(queueId, messageId);

    assertThat(messageDetails).extracting("id", "content")
        .containsExactly(message.messageId(), message.body());
  }

  @Test
  @Ignore
  public void should_list_messages_to_consume() {
    UUID queueId = UUID.randomUUID();
    UUID messageId = UUID.randomUUID();
    var queueCreated = TestHelper.givenQueueCreated();
    var messages = TestHelper.givenMessagesToConsume();

    given(queueService.getQueueAws(queueId)).willReturn(queueCreated);
    given(messageService.getMessages(queueCreated)).willReturn(messages);

    var messageDetails = search.getMessage(queueId, messageId);

    assertThat(messageDetails).isEqualTo(messageDetails);
  }

}
