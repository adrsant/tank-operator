package com.devtools.interactions.queue;

import com.devtools.alloy.http.dtos.sqs.QueueDetailsResponse;
import com.devtools.entity.QueueAws;
import com.devtools.repository.QueueAwsRepository;
import com.devtools.service.QueueService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueSearch {

  private final QueueAwsRepository repository;
  private final QueueService service;

  public Page<QueueDetailsResponse> search(String name, Pageable pageable) {
    return repository.findByNameContaining(name, pageable).map(QueueAws::toQueueDetails);
  }

  public QueueDetailsResponse search(UUID queueId) {
    var queueAws = repository.findById(queueId).orElseThrow();
    return QueueDetailsResponse.builder()
        .id(queueAws.getId())
        .name(queueAws.getName())
        .totalMessages(service.countMessagesInQueue(queueAws.getUrl()))
        .build();
  }
}
