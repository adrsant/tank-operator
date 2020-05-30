package com.devtools.interactions.message;

import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRemoval {

  private final QueueService queueService;
  private final MessageService messageService;

  public void remove(UUID queueId, UUID messageId) {
    var queueAws = queueService.getQueueAws(queueId);
    log.info("removing message {} of queue {}", messageId, queueAws.getName());
    var message = messageService.getMessageById(queueAws, messageId);
    messageService.deleteMessage(queueAws, message);
    log.info("message {} of queue {} has been removed", messageId, queueAws.getName());
  }
}
