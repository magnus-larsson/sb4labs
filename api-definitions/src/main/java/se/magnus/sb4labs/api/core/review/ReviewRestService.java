package se.magnus.sb4labs.api.core.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewRestService {

  /**
   * Sample usage: "curl $HOST:$PORT/review?productId=1".
   *
   * @param productId Id of the product
   * @return the reviews of the product
   */
  @GetMapping(
    value = "/{version}/review",
    version = "1",
    produces = "application/json")
  List<Review> getReviews(@RequestParam(value = "productId", required = true) int productId);
}
