package delivery.calculation.costComputer;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CostComputerApplication {

	@Bean
	public WebClient webClient() {

//		HttpClient httpClient = HttpClient.create()
//				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//				.responseTimeout(Duration.ofMillis(5000))
//				.doOnConnected(conn ->
//						conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
//								.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

		return WebClient.builder()
//				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(CostComputerApplication.class, args);
	}

}
