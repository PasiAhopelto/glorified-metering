package github.pasiahopelto.glorified.metering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CpuTemperatureCollectorTest {

    private static final String TEMPERATURE = "51400";

    private StringReader reader = new StringReader(TEMPERATURE);
    
	private CpuTemperatureCollector collector;

	@Test
	public void failsGracefullyWhenExceptionGetsThrown() throws IOException {
		collector = new CpuTemperatureCollector() {
			@Override
			Reader openProcFileReader() throws FileNotFoundException {
				throw new FileNotFoundException("TEST");
			}
		};
		collector.storeCurrentCpuTemperature();
	}	

	@Test
	public void collectsCpuTemperature() throws IOException {
		collector = new CpuTemperatureCollector() {
			@Override
			Reader openProcFileReader() throws FileNotFoundException {
				return reader;
			}
		};
		collector.storeCurrentCpuTemperature();
	}	
}
