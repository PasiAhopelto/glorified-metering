package github.pasiahopelto.glorified.metering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GpuTemperatureCollector {

	private static final String[] GPU_TEMPERATURE_CMD = new String[] { "vcgencmd", "measure_temp" };
	private Logger logger = LoggerFactory.getLogger(GpuTemperatureCollector.class);

	@Autowired
	private Runtime runtime;
	
	@Scheduled(fixedRate = 5000)
	public void storeCurrentGpuTemperature() {
		try {
			String result = parseTemperature(runTemperatureCommand());
			logger.info("GPU temperature: " + result);
		} catch (IOException e) {
			logger.error("GPU temperature reader failed with " + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("GPU temperature reader failed with " + e.getMessage());
		}
	}

	private String parseTemperature(String temperatureString) {
		return null;
	}

	private String runTemperatureCommand() throws IOException, InterruptedException {
		Process process = runtime.exec(GPU_TEMPERATURE_CMD);
		process.waitFor(2000, TimeUnit.MILLISECONDS);
		InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
		String result = new BufferedReader(inputStream).lines().parallel().collect(Collectors.joining("n"));
		return result;
	}
}
