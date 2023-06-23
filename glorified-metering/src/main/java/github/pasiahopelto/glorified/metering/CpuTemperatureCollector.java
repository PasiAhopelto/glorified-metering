package github.pasiahopelto.glorified.metering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import github.pasiahopelto.glorified.metering.DbWriter.Type;
import github.pasiahopelto.glorified.metering.SpringConfig.ProcFileReaderFactory;

@Component
public class CpuTemperatureCollector {

	private Logger logger = LoggerFactory.getLogger(CpuTemperatureCollector.class);

	@Autowired
	private ProcFileReaderFactory readerFactory;

	@Autowired
	private DbWriter dbWriter;
	
	@Scheduled(fixedRate = 5000)
	public void storeCurrentCpuTemperature() {
		try {
			Float result = parseTemperature(readTemperature());
			dbWriter.writeTemperature(Type.CPU, result);
		} catch (IOException e) {
			logger.error("CPU temperature reader failed with " + e.getMessage());
		}
	}

	private String readTemperature() throws IOException {
		String result = null;
		try (Reader reader = readerFactory.reader();
			 BufferedReader bufferedReader = new BufferedReader(reader)) {
			result = bufferedReader.readLine();
		}
		return result;
	}

	private Float parseTemperature(String temperatureString) {
		return (Float.parseFloat(temperatureString) / 1000f);
	}
}
