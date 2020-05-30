package com.devtools.alloy.http;

import com.devtools.alloy.http.dtos.sqs.QueueDetailsResponse;
import com.devtools.interactions.queue.QueueSearch;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/queues")
@RequiredArgsConstructor
public class QueueController {

  private final QueueSearch interaction;

  @GetMapping(
      params = {"name"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<QueueDetailsResponse> list(
      @RequestParam(required = false) String name, @PageableDefault Pageable pageable) {
    return interaction.search(name, pageable);
  }

  @GetMapping(path = "/{queueId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public QueueDetailsResponse getDetails(@PathVariable UUID queueId) {
    return interaction.search(queueId);
  }
}
