package github.pasiahopelto.glorified.metering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbWriter {
	
	public enum Type {
		CPU,
		GPU
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Logger logger = LoggerFactory.getLogger(DbWriter.class);

	public void writeTemperature(Type type, float celcius) {
		logger.info("writing temperature " + type + " " + celcius);
		jdbcTemplate.update("INSERT INTO metering.temperature (type, temperature) VALUES (?, ?)", type.toString(), celcius);
	}
}
