package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

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

import github.pasiahopelto.glorified.metering.DbWriter.Type;

import org.springframework.jdbc.core.JdbcTemplate;
import org.flywaydb.core.Flyway;

@ExtendWith(MockitoExtension.class)
public class DbWriterTest {

	private static DataSource dataSource = null;
    private static Server server;

	@Spy
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	@InjectMocks
	private DbWriter dbWriter;

	static {
		try {
			server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        dataSource = JdbcConnectionPool.create("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "", "");
	}

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
	public void writesCpuTemperatureToDabase() {
		dbWriter.writeTemperature(Type.CPU, 3.2f);
		dbWriter.writeTemperature(Type.GPU, 4.3f);
		assertEquals(
			3.2f, 
			jdbcTemplate.queryForObject("SELECT temperature FROM metering.temperature WHERE type = 'CPU'", Float.class));
		assertEquals(
			4.3f, 
			jdbcTemplate.queryForObject("SELECT temperature FROM metering.temperature WHERE type = 'GPU'", Float.class));
	}

	@AfterAll
	public static void teardown() {
		server.stop();
	}
}
