package github.pasiahopelto.glorified.metering;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
	
	@Autowired
	private TemperatureReader temperatureReader;
	
    @GetMapping("/temperature")
    public Double getTemperature() {
        return temperatureReader.getCpuTemperatureCelcius();
    }
}