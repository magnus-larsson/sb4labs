package se.magnus.sb4labs.apiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApiConsumerApplication {

    @Bean
    RestClient restClient() {
        return RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()).build();
    }

    public static void main(String[] args) {
		SpringApplication.run(ApiConsumerApplication.class, args);
	}

}
