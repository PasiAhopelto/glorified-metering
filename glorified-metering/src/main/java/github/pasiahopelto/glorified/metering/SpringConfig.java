package github.pasiahopelto.glorified.metering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// FIXME move test implementations to own file, maybe also prod implementations
@Configuration
public class SpringConfig {

	private static final String CPU_TEMPERATURE_FILE = "/sys/class/thermal/thermal_zone0/temp";
	private static final String[] GPU_TEMPERATURE_CMD = new String[] { "vcgencmd", "measure_temp" };

	public interface ProcFileReaderFactory {
		Reader reader() throws FileNotFoundException;
	}

	public interface GpuTemperatureCommandRunnerFactory {
		GpuTemperatureCommandRunner runner();
	}
	
	public interface GpuTemperatureCommandRunner {
		String run() throws IOException, InterruptedException;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "", ""));
	}

	@Bean
	@Profile("!test")
	public GpuTemperatureCommandRunner runnerFactory() {
		return new GpuTemperatureCommandRunner() {
			@Override
			public String run() throws IOException, InterruptedException {
				Process process = Runtime.getRuntime().exec(GPU_TEMPERATURE_CMD);
				process.waitFor(2000, TimeUnit.MILLISECONDS);
				InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
				String result = new BufferedReader(inputStream).lines().parallel().collect(Collectors.joining("n"));
				return result;
			}			
		};
	}


	@Bean
	@Profile("test")
	public GpuTemperatureCommandRunner testRunnerFactory() {
		return new GpuTemperatureCommandRunner() {
			@Override
			public String run() throws IOException, InterruptedException {
				return "temp=45.1'C";
			}			
		};
	}

	@Bean
	@Profile("!test")
	public ProcFileReaderFactory readerFactory() throws FileNotFoundException {
		return new ProcFileReaderFactory() {	
			@Override
			public Reader reader() throws FileNotFoundException {
				return new FileReader(CPU_TEMPERATURE_FILE);
			}
		};
	}

	@Bean
	@Profile("test")
	public ProcFileReaderFactory testReaderFactory() throws FileNotFoundException {
		return new ProcFileReaderFactory() {	
			@Override
			public Reader reader() throws FileNotFoundException {
				return new StringReader("12300");
			}
		};
	}
}
