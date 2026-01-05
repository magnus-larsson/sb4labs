package se.magnus.sb4labs.apiprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.product.ProductRestService;
import se.magnus.sb4labs.api.exceptions.InvalidInputException;
import se.magnus.sb4labs.api.exceptions.NotFoundException;
import java.util.Random;

@RestController
public class ProductRestController implements ProductRestService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

  @Override
  public Product getProduct(int productId, int delay, int faultPercent) {
    LOG.debug("/product v1 return the product for productId={}", productId);

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    if (productId == 13) {
      throw new NotFoundException("No product found for productId: " + productId);
    }

    throwErrorIfBadLuck(faultPercent);

    sleep(delay);

    return new Product(productId, "name-" + productId, 123);
  }

  private void sleep(int delay) {

    if (delay <= 0) return;

    try {
      LOG.debug("Sleeping for {} seconds...", delay);
      Thread.sleep(delay * 1000L);
      LOG.debug("...done sleeping");
    } catch (InterruptedException _) {}
  }

  private void throwErrorIfBadLuck(int faultPercent) {

    if (faultPercent == 0) return;

    int randomThreshold = getRandomNumber(1, 100);

    if (faultPercent < randomThreshold) {
      LOG.debug("We got lucky, no error occurred, {} < {}", faultPercent, randomThreshold);
    } else {
      LOG.info("Bad luck, an error occurred, {} >= {}", faultPercent, randomThreshold);
      throw new RuntimeException("Something went wrong...");
    }

  }

  private final Random randomNumberGenerator = new Random();

  private int getRandomNumber(int min, int max) {

    if (max < min) {
      throw new IllegalArgumentException("Max must be greater than min");
    }

    return randomNumberGenerator.nextInt((max - min) + 1) + min;
  }

}
