package com.devtools.alloy.http;

import com.devtools.alloy.http.dtos.sqs.MessageDetailsResponse;
import com.devtools.alloy.http.dtos.sqs.SendMessageRequest;
import com.devtools.interactions.message.MessageRemoval;
import com.devtools.interactions.message.MessageReprocessing;
import com.devtools.interactions.message.MessageSearch;
import com.devtools.interactions.message.MessageSend;
import com.devtools.interactions.queue.PurgeQueue;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/queues")
@RequiredArgsConstructor
public class QueueMessageController {

  private final MessageSearch messageSearch;
  private final MessageReprocessing reprocessingInteraction;
  private final MessageSend messageSend;
  private final MessageRemoval removalInteraction;
  private final PurgeQueue purgeQueueInteraction;

  @GetMapping(path = "/{queueId}/messages/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public MessageDetailsResponse get(@PathVariable UUID queueId, @PathVariable UUID messageId) {
    return messageSearch.getMessage(queueId, messageId);
  }

  @GetMapping(path = "/{queueId}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<MessageDetailsResponse> list(
      @PathVariable UUID queueId, @PageableDefault Pageable pageable) {
    return messageSearch.list(queueId, pageable);
  }

  @PutMapping(path = "/{queueId}/dead-letter/messages/{messageId}")
  public void reprocess(@PathVariable UUID queueId, @PathVariable UUID messageId) {
    reprocessingInteraction.reprocess(queueId, messageId);
  }

  @PutMapping(path = "/{queueId}/dead-letter")
  public void reprocessAll(@PathVariable UUID queueId) {
    reprocessingInteraction.reprocess(queueId);
  }

  @DeleteMapping(path = "/{queueId}/messages/{messageId}")
  public void remove(@PathVariable UUID queueId, @PathVariable UUID messageId) {
    removalInteraction.remove(queueId, messageId);
  }

  @DeleteMapping(path = "/{queueId}/messages")
  public void removeAll(@PathVariable UUID queueId) {
    purgeQueueInteraction.purge(queueId);
  }

  @PostMapping(path = "/{queueId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void send(@PathVariable UUID queueId, @RequestBody SendMessageRequest message) {
    messageSend.send(queueId, message.getContent());
  }
}
