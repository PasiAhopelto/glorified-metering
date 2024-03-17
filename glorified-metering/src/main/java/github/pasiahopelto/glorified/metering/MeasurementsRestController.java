package github.pasiahopelto.glorified.metering;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.pasiahopelto.glorified.metering.Temperature.Type;

@RestController
@RequestMapping("/measurements")
public class MeasurementsRestController {

	@Autowired
	private TemperatureDbReader temperatureReader;

	@Operation(summary = "Get current GPU temperature in degree Celcius")
	@GetMapping("/temperature/gpu")
	public Temperature getGpuTemperature() {
		return createTemperature(Temperature.Type.GPU, temperatureReader.getGpuTemperatureCelcius());
	}

	@Operation(summary = "Get current CPU temperature in degree Celcius")
	@GetMapping("/temperature/cpu")
	public Temperature getCpuTemperature() {
		return createTemperature(Temperature.Type.CPU, temperatureReader.getCpuTemperatureCelcius());
	}

	private Temperature createTemperature(Type type, Double temperature) {
		Temperature result = new Temperature();
		result.setType(type);
		result.setTemperature(temperature);
		return result;
	}
}
