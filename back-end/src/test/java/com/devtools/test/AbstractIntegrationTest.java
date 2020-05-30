package com.devtools.test;

import com.devtools.test.rule.RulePostgresqlContainer;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class AbstractIntegrationTest {

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = RulePostgresqlContainer.getInstance();
}
