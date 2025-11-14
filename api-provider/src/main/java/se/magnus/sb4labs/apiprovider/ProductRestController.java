package se.magnus.sb4labs.apiprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.api.core.product.ProductRestService;

@RestController
public class ProductRestController implements ProductRestService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product v1 return the product for productId={}", productId);

    return new Product(productId, "name-" + productId, 123);
  }
}
