package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemperatureDbReaderTest {

	private TemperatureDbReader temperatureReader = new TemperatureDbReader();

	@Test
	public void returnsThirtyEightDegreesForGpu() {
		 assertEquals(temperatureReader.getGpuTemperatureCelcius(), Double.valueOf(38.0d));
	}

	@Test
	public void returnsFortyEightDegreesForCpu() {
		 assertEquals(temperatureReader.getCpuTemperatureCelcius(), Double.valueOf(48.0d));
	}
}
