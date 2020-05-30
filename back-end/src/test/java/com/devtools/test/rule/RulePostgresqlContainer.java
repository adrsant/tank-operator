package com.devtools.test.rule;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class RulePostgresqlContainer extends PostgreSQLContainer<RulePostgresqlContainer> {

  private static final String IMAGE_VERSION = "postgres:11.2";
  private static RulePostgresqlContainer container;

  private RulePostgresqlContainer() {
    super(IMAGE_VERSION);
  }

  public static RulePostgresqlContainer getInstance() {
    if (container == null) {
      container = new RulePostgresqlContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    log.info("JVM handles shut down database");
  }
}
