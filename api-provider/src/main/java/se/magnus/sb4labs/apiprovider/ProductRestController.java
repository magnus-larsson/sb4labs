package se.magnus.sb4labs.apiprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.product.ProductRestService;
import se.magnus.sb4labs.api.exceptions.InvalidInputException;
import se.magnus.sb4labs.api.exceptions.NotFoundException;

@RestController
public class ProductRestController implements ProductRestService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product v1 return the product for productId={}", productId);

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    if (productId == 13) {
      throw new NotFoundException("No product found for productId: " + productId);
    }

    try {
      LOG.debug("Sleeping for 3 seconds...");
      Thread.sleep(3000);
      LOG.debug("...done sleeping");
    } catch (InterruptedException _) {}

    return new Product(productId, "name-" + productId, 123);
  }
}
