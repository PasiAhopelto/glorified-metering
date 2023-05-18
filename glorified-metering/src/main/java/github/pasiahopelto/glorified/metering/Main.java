package github.pasiahopelto.glorified.metering;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(ApplicationContext context) {
		return args -> {
		};
	}
}
