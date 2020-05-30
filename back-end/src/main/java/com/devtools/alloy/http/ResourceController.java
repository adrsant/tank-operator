package com.devtools.alloy.http;

import com.devtools.interactions.resource.ResourceSynchronization;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceSynchronization resourceSynchronization;

  @PostMapping(path = "/job/synchronization")
  public void synchronization() {
    resourceSynchronization.synchronization();
  }
}
