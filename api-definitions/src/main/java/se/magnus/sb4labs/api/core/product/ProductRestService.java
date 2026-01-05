package se.magnus.sb4labs.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductRestService {

  /**
   * Sample usage: "curl $HOST:$PORT/product/1".
   *
   * @param productId Id of the product
   * @return the product, if found, else null
   */
  @GetMapping(
    value = "/{version}/product/{productId}",
    version = "1",
    produces = "application/json")
  Product getProduct(
    @PathVariable int productId,
    @RequestParam(value = "delay", required = false, defaultValue = "0") int delay,
    @RequestParam(value = "faultPercent", required = false, defaultValue = "0") int faultPercent
  );
}
