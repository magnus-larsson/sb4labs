package se.magnus.sb4labs.apiconsumer.interfaceclients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.magnus.sb4labs.api.core.product.Product;
import se.magnus.sb4labs.apiconsumer.ProductCompositeRestController;

public class ProductFallbacks {

  final static private Logger LOG = LoggerFactory.getLogger(ProductFallbacks.class);

  public Product getProduct(int productId) {
    LOG.warn("getProduct({}) fallback invoked", productId);
    return (new Product(productId, "Fallback product" + productId, productId));
  }

  // Optional: inspect the failure cause
  public Product getProduct(Throwable cause, int productId) {
    LOG.warn("getProduct({}) fallback invoked, caused by {}", productId, cause.getMessage());
    LOG.warn("getProduct({}) fallback invoked", productId, cause);
    return (new Product(productId, "Fallback product" + productId, productId));
  }
}