package com.devtools.interactions.message;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.devtools.helper.TestHelper;
import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageRemovalTest {

  @Mock
  private MessageService messageService;
  @Mock
  private QueueService queueService;
  @InjectMocks
  private MessageRemoval removal;

  @Test
  public void should_remove_message() {
    UUID queueId = UUID.randomUUID();
    UUID messageId = UUID.randomUUID();
    var queueCreated = TestHelper.givenQueueCreated();
    var message = TestHelper.givenMessageToConsume();

    given(queueService.getQueueAws(queueId)).willReturn(queueCreated);
    given(messageService.getMessageById(queueCreated, messageId)).willReturn(message);

    removal.remove(queueId, messageId);

    then(messageService).should().deleteMessage(queueCreated, message);
  }
}
