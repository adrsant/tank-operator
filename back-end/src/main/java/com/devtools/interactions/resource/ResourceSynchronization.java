package com.devtools.interactions.resource;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceSynchronization {

  private final List<AwsResourceSyncProcess> processes;

  public void synchronization() {
    processes.stream().forEach(AwsResourceSyncProcess::sync);
  }
}
