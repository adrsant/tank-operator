package com.devtools.alloy.http.dtos.sqs;

import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.services.sqs.model.Message;

@Getter
@Builder
public class MessageDetailsResponse {

  private final String id;
  private final String content;

  public static MessageDetailsResponse of(Message message) {
    return MessageDetailsResponse.builder().id(message.messageId()).content(message.body()).build();
  }
}
