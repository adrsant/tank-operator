package com.devtools.interactions.message;

import static java.util.stream.Collectors.toList;

import com.devtools.alloy.http.dtos.sqs.MessageDetailsResponse;
import com.devtools.service.MessageService;
import com.devtools.service.QueueService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSearch {

  private final QueueService queueService;
  private final MessageService messageService;
  private final int MAX_SIZE_MESSAGES = 10;

  public MessageDetailsResponse getMessage(UUID queueId, UUID messageId) {
    var queueAws = queueService.getQueueAws(queueId);
    var message = messageService.getMessageById(queueAws, messageId);
    return MessageDetailsResponse.of(message);
  }

  public Page<MessageDetailsResponse> list(UUID queueId, Pageable pageable) {
    var allMessages = new ArrayList<MessageDetailsResponse>();
    var queueAws = queueService.getQueueAws(queueId);

    final var count = queueService.countMessagesInQueue(queueAws.getUrl());
    var size = count;

    do {
      allMessages.addAll(
          messageService.getMessages(queueAws).stream()
              .map(MessageDetailsResponse::of)
              .collect(toList()));
      size -= MAX_SIZE_MESSAGES;
    } while (size >= 0);

    var partitions = partition(allMessages, pageable.getPageSize());
    var messages = partitions.get(pageable.getPageNumber());

    return new PageImpl<>(messages, pageable, count);
  }

  private List<List<MessageDetailsResponse>> partition(
      final List<MessageDetailsResponse> list, int batchSize) {
    return IntStream.range(0, getNumberOfPartitions(list, batchSize))
        .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
        .collect(toList());
  }

  private int getNumberOfPartitions(List<MessageDetailsResponse> list, int batchSize) {
    return (list.size() + batchSize - 1) / batchSize;
  }
}
