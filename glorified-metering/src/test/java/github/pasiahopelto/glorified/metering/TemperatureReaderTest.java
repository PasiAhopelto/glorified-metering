package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemperatureReaderTest {

	private TemperatureReader temperatureReader = new TemperatureReader();

	@Test
	public void returnsThirtyEightDegreesForGpu() {
		 assertEquals(temperatureReader.getGpuTemperatureCelcius(), Double.valueOf(38.0d));
	}

	@Test
	public void returnsFortyEightDegreesForCpu() {
		 assertEquals(temperatureReader.getCpuTemperatureCelcius(), Double.valueOf(48.0d));
	}
}
