package github.pasiahopelto.glorified.metering;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("temperatureReader")
public class TemperatureDbReader {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Double getGpuTemperatureCelcius() {
		List<Double> results = jdbcTemplate.queryForList("SELECT temperature FROM metering.temperature WHERE type = 'GPU' ORDER BY id DESC LIMIT 1", Double.class);
		return results.isEmpty() ? Double.NaN : results.get(0); 
	}

	public Double getCpuTemperatureCelcius() {
		List<Double> results = jdbcTemplate.queryForList("SELECT temperature FROM metering.temperature WHERE type = 'CPU' ORDER BY id DESC LIMIT 1", Double.class);
		return results.isEmpty() ? Double.NaN : results.get(0); 
	}
}
