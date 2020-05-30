package com.devtools.service;

import com.devtools.entity.QueueAws;
import com.devtools.exceptions.AwsIntegrationException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
public class MessageService {

  public static final int MAX_SIZE_MESSAGES = 10;
  private final SqsClient sqsClient;

  @Value("${message.visibility-timeout}")
  private Integer visibilityTimeout;

  public List<Message> getMessages(QueueAws queue) {
    var request =
        ReceiveMessageRequest.builder()
            .queueUrl(queue.getUrl())
            .maxNumberOfMessages(MAX_SIZE_MESSAGES)
            .visibilityTimeout(visibilityTimeout)
            .build();

    return sqsClient.receiveMessage(request).messages();
  }

  public Message getMessageById(QueueAws queue, UUID messageId) {
    var request =
        ReceiveMessageRequest.builder()
            .queueUrl(queue.getUrl())
            .visibilityTimeout(visibilityTimeout)
            .build();

    return sqsClient.receiveMessage(request).messages().stream()
        .filter(msg -> msg.messageId().equals(messageId.toString()))
        .findFirst()
        .orElseThrow();
  }

  public void deleteMessage(QueueAws queue, Message message) {
    var deleteRequest =
        DeleteMessageRequest.builder()
            .queueUrl(queue.getUrl())
            .receiptHandle(message.receiptHandle())
            .build();

    var response = sqsClient.deleteMessage(deleteRequest).sdkHttpResponse();

    if (!response.isSuccessful()) {
      throw new AwsIntegrationException();
    }
  }

  public void deleteMessageInBatch(QueueAws queue, List<Message> messages) {
    var entries = messages.stream().map(this::toBatchDeleteEntry).collect(Collectors.toList());

    var request =
        DeleteMessageBatchRequest.builder().queueUrl(queue.getUrl()).entries(entries).build();

    var response = sqsClient.deleteMessageBatch(request).sdkHttpResponse();

    if (!response.isSuccessful()) {
      throw new AwsIntegrationException();
    }
  }

  private DeleteMessageBatchRequestEntry toBatchDeleteEntry(Message message) {
    return DeleteMessageBatchRequestEntry.builder().receiptHandle(message.receiptHandle()).build();
  }

  public void sendMessagesInBatch(QueueAws queue, List<Message> messages) {
    var entries = messages.stream().map(this::toBatchSendEntry).collect(Collectors.toList());

    var request =
        SendMessageBatchRequest.builder().entries(entries).queueUrl(queue.getUrl()).build();

    var response = sqsClient.sendMessageBatch(request).sdkHttpResponse();

    if (!response.isSuccessful()) {
      throw new AwsIntegrationException();
    }
  }

  private SendMessageBatchRequestEntry toBatchSendEntry(Message message) {
    return SendMessageBatchRequestEntry.builder().messageBody(message.body()).build();
  }

  public void sendMessage(QueueAws queue, String contentMessage) {
    var request =
        SendMessageRequest.builder().messageBody(contentMessage).queueUrl(queue.getUrl()).build();

    var response = sqsClient.sendMessage(request);

    if (!response.sdkHttpResponse().isSuccessful()) {
      throw new AwsIntegrationException();
    }
  }
}
