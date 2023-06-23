package github.pasiahopelto.glorified.metering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	private static final String CPU_TEMPERATURE_FILE = "/sys/class/thermal/thermal_zone0/temp";

	public interface ProcFileReaderFactory {
		Reader reader() throws FileNotFoundException;
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
