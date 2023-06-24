package com.amigoscode;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.javafaker.Faker;

@Testcontainers
public abstract class  AbstractTestcontainers {

	@Container
	protected static final PostgreSQLContainer<?> postgre_sql_container 
							= new PostgreSQLContainer<>("postgres:latest")
														.withDatabaseName("amigoscode-dao-unit-test")
														.withUsername("amigoscode")
														.withPassword("password"); 
	
	@BeforeAll
	static void beforeAll() {
		  Flyway flyway = Flyway
	                .configure()
	                .dataSource(
	                		postgre_sql_container.getJdbcUrl(),
	                		postgre_sql_container.getUsername(),
	                		postgre_sql_container.getPassword()
	                ).load();
	        flyway.migrate();
	        System.out.println();
	}
	
	//@Test
	void canStartPostgrasDB() {
		assertThat(postgre_sql_container.isRunning()).isTrue();
		assertThat(postgre_sql_container.isCreated()).isTrue();
		//assertThat(postgre_sql_container.isHealthy()).isTrue();
	}
	
	@DynamicPropertySource
	 private static void registerDataSourceProperties(
	            DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", 
			postgre_sql_container::getJdbcUrl
		 );
		 registry.add(
            "spring.datasource.username",
            postgre_sql_container::getUsername
	     );
	     registry.add(
            "spring.datasource.password",
            postgre_sql_container::getPassword
	     );
	}
	
	protected static DataSource getDataSource() {
							return DataSourceBuilder
												.create()
												.driverClassName(postgre_sql_container.getDriverClassName())
												.url(postgre_sql_container.getJdbcUrl())
												.username(postgre_sql_container.getUsername())
												.password(postgre_sql_container.getPassword())
												.build();
	}
	protected static JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
	
	protected static final Faker FAKER = new Faker();
}
