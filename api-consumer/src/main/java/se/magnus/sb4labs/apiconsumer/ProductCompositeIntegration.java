package se.magnus.sb4labs.apiconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.product.ProductService;
import se.magnus.sb4labs.api.core.recommendation.Recommendation;
import se.magnus.sb4labs.api.core.recommendation.RecommendationService;
import se.magnus.sb4labs.api.core.review.Review;
import se.magnus.sb4labs.api.core.review.ReviewService;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

  private final RestClient restClient;
  private final ObjectMapper mapper;

  private final String productServiceUrl;
  private final String recommendationServiceUrl;
  private final String reviewServiceUrl;

  public ProductCompositeIntegration(
    RestClient restClient,
    ObjectMapper mapper,
    AppProperties props) {

    this.restClient = restClient;
    this.mapper = mapper;

    productServiceUrl = "http://" + props.productService().host() + ":" + props.productService().port() + "/product/";
    recommendationServiceUrl = "http://" + props.recommendationService().host() + ":" + props.recommendationService().port() + "/recommendation?productId=";
    reviewServiceUrl = "http://" + props.reviewService().host() + ":" + props.reviewService().port() + "/review?productId=";
  }

  public Product getProduct(int productId) {

    try {
      String url = productServiceUrl + productId;
      LOG.debug("Will call getProduct API on URL: {}", url);

      Product product = restClient.get()
        .uri(url)
        .retrieve()
        .body(Product.class);
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
