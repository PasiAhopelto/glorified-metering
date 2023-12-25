package github.pasiahopelto.glorified.metering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("temperatureReader")
public class TemperatureDbReader {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Double getGpuTemperatureCelcius() {
		return jdbcTemplate.queryForObject("SELECT temperature FROM metering.temperature WHERE type = 'GPU' ORDER BY id DESC LIMIT 1", Double.class);
	}
	
	public Double getCpuTemperatureCelcius() {
		return jdbcTemplate.queryForObject("SELECT temperature FROM metering.temperature WHERE type = 'CPU' ORDER BY id DESC LIMIT 1", Double.class);
	}
}
