package com.devtools.interactions.message;

import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSend {

  private final QueueService queueService;
  private final MessageService messageService;

  public void send(UUID queueId, String contentMessage) {
    var queueAws = queueService.getQueueAws(queueId);
    log.info("sending message to queue {}", queueAws.getName());
    messageService.sendMessage(queueAws, contentMessage);
    log.info("message to queue {} has been sent", queueAws.getName());
  }
}
