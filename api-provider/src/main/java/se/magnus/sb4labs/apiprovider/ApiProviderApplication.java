package se.magnus.sb4labs.apiprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("se.magnus.sb4labs")
public class ApiProviderApplication {

  final static private Logger LOG = LoggerFactory.getLogger(ApiProviderApplication.class);

  static void main(String[] args) {
    SpringApplication.run(ApiProviderApplication.class, args);
    LOG.info("ApiProviderApplication v1 started");
  }

}
