package com.devtools.interactions.resource;

import com.devtools.entity.QueueAws;
import com.devtools.enumerations.QueueType;
import com.devtools.helper.ParseJsonHelper;
import com.devtools.repository.QueueAwsRepository;
import com.devtools.service.QueueService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueueTagsRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueSynchronization implements AwsResourceSyncProcess {

  private final QueueAwsRepository repository;
  private final QueueService queueService;
  private final SqsClient sqsClient;

  @Override
  public void sync() {
    var urls = sqsClient.listQueues().queueUrls();
    List<QueueAws> queues = urls.stream().map(this::mapQueue).collect(Collectors.toList());

    repository.saveAll(queues);
  }

  private String getTags(String url) {
    var tagsRequest = ListQueueTagsRequest.builder().queueUrl(url).build();
    var tags = sqsClient.listQueueTags(tagsRequest).tags();
    return ParseJsonHelper.toJson(tags);
  }

  private String getName(String arn) {
    return Stream.of(arn.split(":")).reduce((first, second) -> second).orElseThrow();
  }

  private QueueAws mapQueue(String queueUrl) {
    var arn = queueService.getQueueArn(queueUrl);
    log.info("Updating queue {}", getName(arn));
    return QueueAws.builder()
        .url(queueUrl)
        .arn(arn)
        .queueType(QueueType.DEFAULT)
        .name(getName(arn))
        .tags(getTags(queueUrl))
        .build();
  }
}
