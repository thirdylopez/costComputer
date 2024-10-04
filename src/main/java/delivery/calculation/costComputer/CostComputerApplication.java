package delivery.calculation.costComputer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CostComputerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostComputerApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

}
