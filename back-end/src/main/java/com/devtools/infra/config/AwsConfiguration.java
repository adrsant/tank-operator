package com.devtools.infra.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@Profile("!test")
public class AwsConfiguration {

  @Bean
  @Profile("!development")
  public SqsClient createSQSConnectionFactory() {
    return SqsClient.builder().build();
  }

  @Bean
  @Profile("development")
  public SqsClient createSQSConnectionFactoryLocal(@Value("${aws.sqs.url}") String url) {
    return SqsClient.builder().endpointOverride(URI.create(url)).build();
  }
}
