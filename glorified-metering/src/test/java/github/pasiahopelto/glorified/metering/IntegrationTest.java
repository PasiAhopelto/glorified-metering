package github.pasiahopelto.glorified.metering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class IntegrationTest {

    private static WebClient client;

    @Autowired
    private GpuTemperatureCollector gpuTemperatureCollector;

    @Autowired
    private CpuTemperatureCollector cpuTemperatureCollector;

    private Object actualStatusCode;
    private String actualTemperature;

    @BeforeAll
    public static void beforeEach() {
        Main.main(new String[0]);
        client = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .build();
    }

    @Test
    public void canQueryCpuTemperature() {
        cpuTemperatureCollector.storeCurrentCpuTemperature();
        Mono<ResponseEntity<String>> response = getResponse("cpu");
        checkResult(response);
        assertEquals("12.3", actualTemperature);
        assertEquals(HttpStatusCode.valueOf(200), actualStatusCode);
    }

    @Test
    public void canQueryGpuTemperature() {
        gpuTemperatureCollector.storeCurrentGpuTemperature();
        Mono<ResponseEntity<String>> response = getResponse("gpu");
        checkResult(response);
        assertEquals("45.1", actualTemperature);
        assertEquals(HttpStatusCode.valueOf(200), actualStatusCode);
    }

    private void checkResult(Mono<ResponseEntity<String>> response) {
        response.subscribe(
            temperature -> {
                actualTemperature = temperature.getBody();
                actualStatusCode = temperature.getStatusCode();
            },
            error -> {
                System.err.println("got error: " + error);
            }
        );
        response.block();
    }

    private Mono<ResponseEntity<String>> getResponse(String type) {
        Mono<ResponseEntity<String>> response = client
            .get()
            .uri("/measurements/temperature/" + type)
            .retrieve()
            .toEntity(String.class);
        return response;
    }
}