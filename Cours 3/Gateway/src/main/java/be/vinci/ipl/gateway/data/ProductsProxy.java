package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@Repository
@FeignClient(name = "products")
public interface ProductsProxy {
    @GetMapping("/products/{id}")
    Product readProduct(int id);
}
