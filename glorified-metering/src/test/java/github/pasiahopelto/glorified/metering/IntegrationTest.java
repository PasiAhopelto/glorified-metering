package github.pasiahopelto.glorified.metering;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTest {

    private static WebClient client;

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
        Mono<ResponseEntity<String>> response = client
            .get()
            .uri("/measurements/temperature/cpu")
            .retrieve()
            .toEntity(String.class);
        response.block();
    }

    @Test
    public void canQueryGpuTemperature() {
        Mono<ResponseEntity<String>> response = client
            .get()
            .uri("/measurements/temperature/gpu")
            .retrieve()
            .toEntity(String.class);
        response.block();
    }
}