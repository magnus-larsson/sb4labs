package se.magnus.sb4labs.apiconsumer.interfaceclients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import se.magnus.sb4labs.api.core.product.Product;

@HttpExchange("/product")
public interface ProductClient {

	@GetExchange("/{productId}")
  Product getProduct(
    @PathVariable int productId,
    @RequestParam(value = "delay", required = false, defaultValue = "0") int delay,
    @RequestParam(value = "faultPercent", required = false, defaultValue = "0") int faultPercent
  );
}
