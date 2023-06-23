package github.pasiahopelto.glorified.metering;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import github.pasiahopelto.glorified.metering.DbWriter.Type;

@ExtendWith(MockitoExtension.class)
public class GpuTemperatureCollectorTest {

	private static final String[] GPU_TEMPERATURE_CMD = new String[] { "vcgencmd", "measure_temp" };
    private static final String TEMPERATURE = "temp=59.4'C";

    @Mock
    private DbWriter dbWriter;
    
	@Mock
	private Runtime runtime;
	
	@InjectMocks
	private GpuTemperatureCollector collector;

    private Process process = mock(Process.class);

	@Test
	public void failsGracefullyIfCollectorProcessFails() throws IOException {
		when(runtime.exec(new String[] { "vcgencmd", "measure_temp" })).thenThrow(new IOException());
		collector.storeCurrentGpuTemperature();
		verifyNoInteractions(dbWriter);
	}
	
	@Test
    public void storesCurrentGpuTemperature() throws IOException, InterruptedException {
        when(runtime.exec(GPU_TEMPERATURE_CMD)).thenReturn(process);
		when(process.getInputStream()).thenReturn(new ByteArrayInputStream(TEMPERATURE.getBytes()));
        collector.storeCurrentGpuTemperature();
		verify(dbWriter, times(1)).writeTemperature(Type.GPU, 59.4f);
    }
}
