package github.pasiahopelto.glorified.metering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SpringConfig {

	private static final String CPU_TEMPERATURE_FILE = "/sys/class/thermal/thermal_zone0/temp";

	@Autowired
	private DataSourceProperties dataSourceProperties;

	public interface ProcFileReaderFactory {
		Reader reader() throws FileNotFoundException;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		String url = dataSourceProperties.determineUrl();
		String username = dataSourceProperties.determineUsername();
		String password = dataSourceProperties.determinePassword();
		return new JdbcTemplate(new DriverManagerDataSource(url, username, password));
	}

	@Bean
	public Runtime runtime() throws Exception {
		return Runtime.getRuntime();
	}
	
	@Bean
	public ProcFileReaderFactory readerFactory() throws FileNotFoundException {
		return new ProcFileReaderFactory() {	
			@Override
			public Reader reader() throws FileNotFoundException {
				return new FileReader(CPU_TEMPERATURE_FILE);
			}
		};
	}
}
