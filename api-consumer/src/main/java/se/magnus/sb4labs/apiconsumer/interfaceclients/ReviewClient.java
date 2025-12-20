package se.magnus.sb4labs.apiconsumer.interfaceclients;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import se.magnus.sb4labs.api.core.review.Review;

import java.util.List;

@HttpExchange("/review")
public interface ReviewClient {

  @GetExchange
  List<Review> getReviews(@RequestParam int productId);
}
