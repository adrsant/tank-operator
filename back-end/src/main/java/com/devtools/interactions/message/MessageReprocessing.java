package com.devtools.interactions.message;

import com.devtools.entity.QueueAws;
import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReprocessing {

  private final QueueService queueService;
  private final MessageService messageService;

  public void reprocess(UUID queueId, UUID messageId) {
    var targetQueue = queueService.getQueueAws(queueId);
    var deadLetter = targetQueue.getDeadLetter();
    var message = messageService.getMessageById(deadLetter, messageId);
    reprocessMessage(targetQueue, message);
  }

  public void reprocess(UUID queueId) {
    var targetQueue = queueService.getQueueAws(queueId);
    var deadLetter = targetQueue.getDeadLetter();

    while (queueService.isNotEmpty(deadLetter)) {
      var messages = messageService.getMessages(deadLetter);
      reprocessMessageInBatch(targetQueue, messages);
    }
  }

  private void reprocessMessage(QueueAws queue, Message message) {
    log.info("redelivering message {} to queue {}", message.messageId(), queue.getName());
    messageService.sendMessage(queue, message.body());
    messageService.deleteMessage(queue.getDeadLetter(), message);
    log.info("message {} has been redelivered to queue {}", message.messageId(), queue.getName());
  }

  private void reprocessMessageInBatch(QueueAws queue, List<Message> messages) {
    log.info("redelivering {} messages to queue {}", messages.size(), queue.getName());
    messageService.sendMessagesInBatch(queue, messages);
    messageService.deleteMessageInBatch(queue.getDeadLetter(), messages);
    log.info("{} messages {} has been redelivered to queue {}", messages.size(), queue.getName());
  }
}
