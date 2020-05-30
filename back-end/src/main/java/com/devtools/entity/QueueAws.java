package com.devtools.entity;

import com.devtools.alloy.http.dtos.sqs.QueueDetailsResponse;
import com.devtools.enumerations.QueueType;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "arn")
public class QueueAws {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne private QueueAws deadLetter;
  private String arn;
  private String url;
  private String name;
  private String tags;

  @Enumerated(EnumType.STRING)
  private QueueType queueType;

  @Builder.Default private boolean enabled = true;

  public QueueDetailsResponse toQueueDetails() {
    return QueueDetailsResponse.builder().id(id).name(name).build();
  }
}
