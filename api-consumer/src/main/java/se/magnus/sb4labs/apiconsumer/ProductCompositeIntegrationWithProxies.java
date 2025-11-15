package se.magnus.sb4labs.apiconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.product.ProductRestService;
import se.magnus.sb4labs.api.core.recommendation.Recommendation;
import se.magnus.sb4labs.api.core.recommendation.RecommendationRestService;
import se.magnus.sb4labs.api.core.review.Review;
import se.magnus.sb4labs.api.core.review.ReviewRestService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCompositeIntegrationWithProxies implements ProductRestService, RecommendationRestService, ReviewRestService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegrationWithProxies.class);

  private final RestClient restClient;

  private final String recommendationServiceUrl;
  private final String reviewServiceUrl;

  private final ProductRestService productClient;

  public ProductCompositeIntegrationWithProxies(
    RestClient restClient,
    AppProperties props) {

    this.restClient = restClient;

    recommendationServiceUrl = "http://" + props.recommendationService().host() + ":" + props.recommendationService().port() + "/recommendation?productId=";
    reviewServiceUrl = "http://" + props.reviewService().host() + ":" + props.reviewService().port() + "/review?productId=";

    String productBaseUrl = "http://" + props.productService().host() + ":" + props.productService().port();
    RestClient productRestClient = RestClient.builder().
      baseUrl(productBaseUrl).
      apiVersionInserter(ApiVersionInserter.usePathSegment(0)).
      defaultApiVersion("1").
      build();
    HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builder()
      .exchangeAdapter(RestClientAdapter.create(productRestClient))
      .build();
    productClient = proxyFactory.createClient(ProductRestService.class);

  }

  public Product getProduct(int productId) {

    try {
      LOG.debug("Will use a HttpServiceProxyFactory to call getProduct API with product id: {}", productId);
      Product product = productClient.getProduct(productId);
      LOG.debug("Found a product with id: {}", product.productId());

      return product;

    } catch (HttpClientErrorException ex) {
        LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        LOG.warn("Error body: {}", ex.getResponseBodyAsString());
        throw ex;
      }
    }


  public List<Recommendation> getRecommendations(int productId) {

    try {
      String url = recommendationServiceUrl + productId;

      LOG.debug("Will call getRecommendations API on URL: {}", url);
      List<Recommendation> recommendations = restClient.get()
        .uri(url)
        .apiVersion("1")
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});

      LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
      return recommendations;

    } catch (Exception ex) {
      LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
      return new ArrayList<>();
    }
  }

  public List<Review> getReviews(int productId) {

    try {
      String url = reviewServiceUrl + productId;

      LOG.debug("Will call getReviews API on URL: {}", url);
      List<Review> reviews = restClient.get()
        .uri(url)
        .apiVersion("1")
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});

      LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
      return reviews;

    } catch (Exception ex) {
      LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
      return new ArrayList<>();
    }
  }
}
