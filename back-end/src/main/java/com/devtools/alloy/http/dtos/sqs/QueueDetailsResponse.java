package com.devtools.alloy.http.dtos.sqs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class QueueDetailsResponse {

  private final UUID id;
  private final String name;
  private final Long totalMessages;
}
