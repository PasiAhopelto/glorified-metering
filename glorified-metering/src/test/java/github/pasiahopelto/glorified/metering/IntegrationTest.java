package github.pasiahopelto.glorified.metering;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private Main main;

    @Autowired
    private CommandLineRunner runner;

    @Test
    public void canQueryCpuTemperature() {
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<ResponseEntity<String>> entity = client
            .get()
            .uri("/measurements/temperature/cpu")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(String.class);
        // entity.block();
        System.err.println(entity);
    }
}