package github.pasiahopelto.glorified.metering;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public Runtime runtime() throws Exception {
		return Runtime.getRuntime();
	}
}
