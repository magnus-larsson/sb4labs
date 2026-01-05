package se.magnus.sb4labs.api.composite.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductCompositeRestService {

  /**
   * Sample usage: "curl $HOST:$PORT/product-composite/1".
   *
   * @param productId Id of the product
   * @return the composite product info, if found, else null
   */
  @GetMapping(
    value = "/product-composite/{productId}",
    produces = "application/json")
  ProductAggregate getProduct(
    @PathVariable int productId,
    @RequestParam(value = "delay", required = false, defaultValue = "0") int delay,
    @RequestParam(value = "faultPercent", required = false, defaultValue = "0") int faultPercent
  );
}
