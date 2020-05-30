package com.devtools.interactions.queue;

import com.devtools.service.QueueService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.PurgeQueueRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurgeQueue {

  private final SqsClient sqsClient;
  private final QueueService queueService;

  public void purge(UUID queueId) {
    log.info("purging queue {}", queueId);
    var queueAws = queueService.getQueueAws(queueId);
    var request = PurgeQueueRequest.builder().queueUrl(queueAws.getUrl()).build();

    sqsClient.purgeQueue(request);

    log.info("queue {} has been purged", queueId);
  }
}
