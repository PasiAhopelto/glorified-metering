package github.pasiahopelto.glorified.metering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import github.pasiahopelto.glorified.metering.DbWriter.Type;

@Component
public class GpuTemperatureCollector {

	private static final Pattern TEMPERATURE_PATTERN = Pattern.compile("temp=(\\d+\\.{0,1}\\d*)'C");
	private static final String[] GPU_TEMPERATURE_CMD = new String[] { "vcgencmd", "measure_temp" };
	private Logger logger = LoggerFactory.getLogger(GpuTemperatureCollector.class);

	@Autowired
	private DbWriter dbWriter;
	
	@Autowired
	private Runtime runtime;
	
	@Scheduled(fixedRate = 5000)
	public void storeCurrentGpuTemperature() {
		try {
			Float result = parseTemperature(runTemperatureCommand());
			dbWriter.writeTemperature(Type.GPU, result);
		} catch (IOException e) {
			logger.error("GPU temperature reader failed with " + e.getMessage());
		} catch (InterruptedException e) {
			logger.error("GPU temperature reader failed with " + e.getMessage());
		}
	}

	private Float parseTemperature(String temperatureString) {
		String result = null;
		if(temperatureString != null) {
			Matcher matcher = TEMPERATURE_PATTERN.matcher(temperatureString);
			if(matcher.matches()) {
				result = matcher.group(1);
			}
		}
		return (result == null ? null : Float.parseFloat(result));
	}

	private String runTemperatureCommand() throws IOException, InterruptedException {
		Process process = runtime.exec(GPU_TEMPERATURE_CMD);
		process.waitFor(2000, TimeUnit.MILLISECONDS);
		InputStreamReader inputStream = new InputStreamReader(process.getInputStream());
		String result = new BufferedReader(inputStream).lines().parallel().collect(Collectors.joining("n"));
		return result;
	}
}
