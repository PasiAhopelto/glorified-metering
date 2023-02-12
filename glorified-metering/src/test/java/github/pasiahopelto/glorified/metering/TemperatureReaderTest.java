package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemperatureReaderTest {

	private TemperatureReader temperatureReader = new TemperatureReader();

	@Test
	public void returnsThirtyEightDegrees() {
		 assertEquals(temperatureReader.getGpuTemperatureCelcius(), Double.valueOf(38.0d));
	}
}
