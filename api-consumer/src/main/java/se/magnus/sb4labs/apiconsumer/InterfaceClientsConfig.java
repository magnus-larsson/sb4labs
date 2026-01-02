package se.magnus.sb4labs.apiconsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.HttpServiceGroup;
import org.springframework.web.service.registry.ImportHttpServices;
import se.magnus.sb4labs.apiconsumer.interfaceclients.ProductClient;
import se.magnus.sb4labs.apiconsumer.interfaceclients.RecommendationClient;
import se.magnus.sb4labs.apiconsumer.interfaceclients.ReviewClient;

import java.io.IOException;

@ImportHttpServices(group = "productGroup", types = ProductClient.class)
@ImportHttpServices(group = "recommendationGroup", types = RecommendationClient.class)
@ImportHttpServices(group = "reviewGroup", types = ReviewClient.class)
//@ImportHttpServices(group = "internalApis", basePackages = "se.magnus.sb4labs.apiconsumer.interfaceclients")
@Configuration
public class InterfaceClientsConfig {

  private static final Logger logger = LogManager.getLogger(InterfaceClientsConfig.class);

  private final AppProperties props;

  public InterfaceClientsConfig(AppProperties props) {
    this.props = props;
  }

  @Bean
  RestClientHttpServiceGroupConfigurer groupConfigurer() {
    return groups -> {

      groups.forEachClient((group, builder) -> {
        logger.info("Configuring group {}...", group.name());
        builder
//          .baseUrl("http://localhost:7001")
//          .defaultApiVersion("1")
          .defaultHeader("Accept", "application/json")
          .apiVersionInserter(ApiVersionInserter.usePathSegment(0));
      });

//      groups.filterByName("internalApis")
//        .forEachClient((group, builder) -> {
//          logger.info("Configuring the internalApis group...");
//          builder
////          .baseUrl("http://localhost:7001")
////          .defaultApiVersion("1")
//            .defaultHeader("Accept", "application/json")
//            .apiVersionInserter(ApiVersionInserter.usePathSegment(0));
//        });

      groups.forEachClient((cb, builder) -> builder.requestInterceptor(new LoggingInterceptor()));
    };
  }

  private static String getBaseUrl(HttpServiceGroup group) {
//    return "http://" + switch (group.name()) {
//      case Integer i -> i.doubleValue();
//      case Float f -> f.doubleValue();
//      case String s -> Double.parseDouble(s);
//      default -> 0d;
//    };
    return "http://localhost:7001";
  }

  private static class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @NullMarked
    public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

      logger.info("""
					Performing request {} {}
					{}
					""", request.getMethod(), request.getURI(), request.getHeaders());

      return execution.execute(request, body);
    }
  }

}
