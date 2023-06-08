package github.pasiahopelto.glorified.metering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CpuTemperatureCollector {

	private static final String CPU_TEMPERATURE_FILE = "/sys/class/thermal/thermal_zone0/temp";
	private Logger logger = LoggerFactory.getLogger(CpuTemperatureCollector.class);

	@Scheduled(fixedRate = 5000)
	public void storeCurrentCpuTemperature() {
		try {
			Float result = parseTemperature(readTemperature());
			logger.info("CPU temperature: " + result);
		} catch (IOException e) {
			logger.error("CPU temperature reader failed with " + e.getMessage());
		}
	}

	private String readTemperature() throws IOException {
		String result = null;
		try (Reader reader = openProcFileReader();
			 BufferedReader bufferedReader = new BufferedReader(reader)) {
			result = bufferedReader.readLine();
		}
		return result;
	}

	Reader openProcFileReader() throws FileNotFoundException {
		return new FileReader(CPU_TEMPERATURE_FILE);
	}

	private Float parseTemperature(String temperatureString) {
		return (Float.parseFloat(temperatureString) / 1000f);
	}
}
