package se.magnus.sb4labs.apiconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApiConsumerApplication {

  final static private Logger LOG = LoggerFactory.getLogger(ApiConsumerApplication.class);

  @Bean
  RestClient restClient() {
    return RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()).build();
  }

  static void main(String[] args) {
    SpringApplication.run(ApiConsumerApplication.class, args);
    LOG.info("ApiConsumerApplication v2 started");
  }

}
