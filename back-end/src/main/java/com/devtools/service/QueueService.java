package com.devtools.service;

import com.devtools.entity.QueueAws;
import com.devtools.exceptions.AwsResourceNotFoundException;
import com.devtools.repository.QueueAwsRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Service
@RequiredArgsConstructor
public class QueueService {

  private final SqsClient sqsClient;
  private final QueueAwsRepository repository;

  @Value("${message.visibility-timeout}")
  private Integer visibilityTimeout;

  public QueueAws getQueueAws(UUID queueId) {
    return repository.findById(queueId).orElseThrow(AwsResourceNotFoundException::new);
  }

  public String getQueueArn(String queueUrl) {
    return getAttribute(queueUrl, QueueAttributeName.QUEUE_ARN);
  }

  public Long countMessagesInQueue(String queueUrl) {
    return Long.valueOf(getAttribute(queueUrl, QueueAttributeName.APPROXIMATE_NUMBER_OF_MESSAGES));
  }

  public boolean isNotEmpty(QueueAws queue) {
    var request =
        ReceiveMessageRequest.builder()
            .queueUrl(queue.getUrl())
            .visibilityTimeout(visibilityTimeout)
            .build();

    return sqsClient.receiveMessage(request).hasMessages();
  }

  public String getAttribute(String queueUrl, QueueAttributeName attributeName) {
    var request =
        GetQueueAttributesRequest.builder()
            .attributeNames(attributeName)
            .queueUrl(queueUrl)
            .build();
    return sqsClient.getQueueAttributes(request).attributes().get(attributeName);
  }
}
