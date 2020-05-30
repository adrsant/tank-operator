package com.devtools.test;

import com.devtools.test.rule.RulePostgresqlContainer;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public abstract class AbstractTestRepository {

  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = RulePostgresqlContainer.getInstance();
}
