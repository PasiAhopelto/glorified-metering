package github.pasiahopelto.glorified.metering;

import org.springframework.stereotype.Component;

@Component("temperatureReader")
public class TemperatureReader {

	public Double getGpuTemperatureCelcius() {
		return 38.0;
	}
}
