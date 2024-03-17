package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MeasurementRestControllerTest {

	@Mock
	private TemperatureDbReader temperatureReader;

	@InjectMocks
	private MeasurementsRestController controller;

	@Test
	public void returnsTenDegreesForGpu() {
		when(temperatureReader.getGpuTemperatureCelcius()).thenReturn(Double.valueOf(10.0d));
		assertEquals(createExpectedTemperature(Temperature.Type.GPU, 10.0), controller.getGpuTemperature());
	}

	@Test
	public void returnsTwentyDegreesForCpu() {
		when(temperatureReader.getCpuTemperatureCelcius()).thenReturn(Double.valueOf(20.0d));
		assertEquals(createExpectedTemperature(Temperature.Type.CPU, 20.0), controller.getCpuTemperature());
	}

	private Temperature createExpectedTemperature(Temperature.Type type, Double temperature) {
		Temperature result = new Temperature();
		result.setType(type);
		result.setTemperature(temperature);
		return result;
	}
 }
