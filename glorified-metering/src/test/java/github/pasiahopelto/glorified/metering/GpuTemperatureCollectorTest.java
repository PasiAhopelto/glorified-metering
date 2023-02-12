package github.pasiahopelto.glorified.metering;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GpuTemperatureCollectorTest {

	private static final String[] GPU_TEMPERATURE_CMD = new String[] { "vcgencmd", "measure_temp" };
    private static final String TEMPERATURE = "temp=59.4'C";

	@Mock
	private Runtime runtime;
	
	@InjectMocks
	private GpuTemperatureCollector collector;

    private Process process = mock(Process.class);

	@Test
	public void failsGracefullyIfCollectorProcessFails() throws IOException {
		when(runtime.exec(new String[] { "vcgencmd", "measure_temp" })).thenThrow(new IOException());
		collector.storeCurrentGpuTemperature();
	}
	
	@Test
    public void storesCurrentGpuTemperature() throws IOException, InterruptedException {
        when(runtime.exec(GPU_TEMPERATURE_CMD)).thenReturn(process);
		when(process.getInputStream()).thenReturn(new ByteArrayInputStream(TEMPERATURE.getBytes()));
        collector.storeCurrentGpuTemperature();
    }
}
