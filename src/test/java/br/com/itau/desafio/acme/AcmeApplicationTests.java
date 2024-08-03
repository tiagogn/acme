package br.com.itau.desafio.acme;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = AcmeApplicationTests.BaseConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringRabbitTest
@SpringJUnitConfig
@AutoConfigureMockMvc
public class AcmeApplicationTests {

	private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = br.com.itau.desafio.acme.infra.config.PostgreSQLContainer.getInstance()
			.withDatabaseName("acme")
			.withUsername("acme_user")
			.withPassword("acme_password");

	static {
		POSTGRESQL_CONTAINER.start();
	}

	@Autowired
	private Flyway flyway;

	@BeforeEach
	public void setUp() {
		flyway.clean();
		flyway.migrate();
	}

	@TestConfiguration
    static class BaseConfiguration {

	}
}
