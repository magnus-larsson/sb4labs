package se.magnus.sb4labs.apiconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.sb4labs.api.composite.product.*;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.recommendation.Recommendation;
import se.magnus.sb4labs.api.core.review.Review;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

import static java.util.concurrent.StructuredTaskScope.Subtask;

@RestController
public class ProductCompositeRestController implements ProductCompositeRestService {

  final static private Logger LOG = LoggerFactory.getLogger(ProductCompositeRestController.class);

  final private ProductCompositeIntegration integration;

  public ProductCompositeRestController(ProductCompositeIntegration integration) {
    this.integration = integration;
  }

  @Override
  public ProductAggregate getProduct(int productId) {

    LOG.debug("Calling the three APIs in parallell using StructuredTaskScope...");

    try (var scope = StructuredTaskScope.open()) {

      Subtask<Product> productSubTask = scope.fork(() -> integration.getProduct(productId));
      Subtask<List<Recommendation>> recommendationsSubTask = scope.fork(() -> integration.getRecommendations(productId));
      Subtask<List<Review>> reviewsSubTask = scope.fork(() -> integration.getReviews(productId));

      scope.join();

      return createProductAggregate(productSubTask.get(), recommendationsSubTask.get(), reviewsSubTask.get());

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private ProductAggregate createProductAggregate(
    Product product,
    List<Recommendation> recommendations,
    List<Review> reviews) {

    // 1. Setup product info
    int productId = product.productId();
    String name = product.name();
    int weight = product.weight();

    // 2. Copy summary recommendation info, if available
    List<RecommendationSummary> recommendationSummaries =
      (recommendations == null) ? null : recommendations.stream()
        .map(r -> new RecommendationSummary(r.recommendationId(), r.author(), r.rate(), r.content()))
        .toList();

    // 3. Copy summary review info, if available
    List<ReviewSummary> reviewSummaries =
      (reviews == null) ? null : reviews.stream()
        .map(r -> new ReviewSummary(r.reviewId(), r.author(), r.subject(), r.content()))
        .toList();

    return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries);
  }

    @GetMapping("/thread-info")
    public String getThreadName() {
      var ct = Thread.currentThread();
      String threadInfo = MessageFormat.format("Thread info, name = {0}, is virtual = {1}, thread class = {2}",
        ct.toString(),
        ct.isVirtual(),
        ct.getClass().getName());
      LOG.debug("/thread-info returns: {}", threadInfo);
      return threadInfo;
    }

}
