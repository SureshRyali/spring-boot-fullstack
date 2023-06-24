package com.amigoscode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainerTest extends AbstractTestcontainers{
	@Test
	void canStartPostgrasDB() {
		assertThat(postgre_sql_container.isRunning()).isTrue();
		assertThat(postgre_sql_container.isCreated()).isTrue();
		//assertThat(postgre_sql_container.isHealthy()).isTrue();
	}
}



















