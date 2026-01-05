package se.magnus.sb4labs.apiconsumer.interfaceclients;

import org.springframework.cloud.client.circuitbreaker.httpservice.HttpServiceFallback;
import org.springframework.context.annotation.Configuration;

@Configuration
@HttpServiceFallback(
  group = "productGroup",
  service = ProductClient.class,
  value = ProductFallbacks.class)
class ProductClientFallbackConfig {}
