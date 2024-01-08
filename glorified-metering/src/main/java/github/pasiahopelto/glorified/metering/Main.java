package github.pasiahopelto.glorified.metering;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "", ""));
		Flyway flyway = Flyway.configure().dataSource(jdbcTemplate.getDataSource()).load();
		flyway.migrate();
	}
	
	@Bean
	public CommandLineRunner runner(ApplicationContext context) {
		return args -> {
		};
	}
}
