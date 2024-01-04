package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import github.pasiahopelto.glorified.metering.DbWriter.Type;

@ExtendWith(MockitoExtension.class)
public class DbOperationsTest {

	private static DataSource dataSource = null;
    private static Server server;

	static {
		try {
			server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        dataSource = JdbcConnectionPool.create("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "", "");
	}

	@Spy
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	@InjectMocks
	private TemperatureDbReader temperatureReader;

	@InjectMocks
	private DbWriter dbWriter;

	@BeforeAll
	public static void setup() throws SQLException {
 		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		flyway.migrate();
	}

	@BeforeEach
	public void beforeEach() {
		jdbcTemplate.update("DELETE FROM metering.temperature");
	}

	@Test
	public void nanIfCpuTableIsEmpty() {
		assertEquals(Double.NaN, temperatureReader.getCpuTemperatureCelcius());
	}

	@Test
	public void nanIfGpuTableIsEmpty() {
		assertEquals(Double.NaN, temperatureReader.getGpuTemperatureCelcius());
	}

	@Test
	public void returnsStoredTemperatures() {
		dbWriter.writeTemperature(Type.CPU, 1.1f);
		dbWriter.writeTemperature(Type.GPU, 2.1f);
		assertEquals(Double.valueOf(1.1f), temperatureReader.getCpuTemperatureCelcius(), 0.05);
		assertEquals(Double.valueOf(2.1f), temperatureReader.getGpuTemperatureCelcius(), 0.05);
	}

	@Test
	public void returnsNewestStoredTemperatures() throws SQLException {
		dbWriter.writeTemperature(Type.CPU, 1.1f);
		dbWriter.writeTemperature(Type.CPU, 0.1f);
		dbWriter.writeTemperature(Type.GPU, 2.1f);
		dbWriter.writeTemperature(Type.GPU, 0.2f);
		assertEquals(Double.valueOf(0.1f), temperatureReader.getCpuTemperatureCelcius(), 0.05);
		assertEquals(Double.valueOf(0.2f), temperatureReader.getGpuTemperatureCelcius(), 0.05);
	}

	@AfterAll
	public static void teardown() {
		server.stop();
	}
}
