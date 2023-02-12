package github.pasiahopelto.glorified.metering;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GpuTemperatureCollectorTest {

	@Mock
	private Runtime runtime;
	
	@InjectMocks
	private GpuTemperatureCollector collector;

	@Test
	public void failsGracefullyIfCollectorProcessFails() throws IOException {
		when(runtime.exec(new String[] { "vcgencmd", "measure_temp" })).thenThrow(new IOException());
		collector.storeCurrentGpuTemperature();
	}
	
	// TODO continue with tests that lead to green path
}
