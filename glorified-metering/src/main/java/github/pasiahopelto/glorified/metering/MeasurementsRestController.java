package github.pasiahopelto.glorified.metering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
public class MeasurementsRestController {

	@Autowired
	private TemperatureDbReader temperatureReader;

	@GetMapping("/temperature/gpu")
	public Double getGpuTemperature() {
		return temperatureReader.getGpuTemperatureCelcius();
	}

	@GetMapping("/temperature/cpu")
	public Double getCpuTemperature() {
		return temperatureReader.getCpuTemperatureCelcius();
	}
}
