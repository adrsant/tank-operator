package com.devtools.helper;

import com.devtools.entity.QueueAws;
import com.devtools.enumerations.QueueType;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import software.amazon.awssdk.services.sqs.model.Message;

@UtilityClass
public class TestHelper {

  public QueueAws givenQueueCreated() {
    return QueueAws.builder()
        .id(UUID.randomUUID())
        .arn("queue_arn")
        .name("queue_name")
        .url("queque_url")
        .queueType(QueueType.DEFAULT)
        .enabled(true)
        .build();
  }

  public Message givenMessageToConsume() {
    return Message.builder()
        .body("BODY")
        .messageId(UUID.randomUUID().toString())
        .build();
  }

  public List<Message> givenMessagesToConsume() {
    return List.of(givenMessageToConsume(), givenMessageToConsume());
  }

}
