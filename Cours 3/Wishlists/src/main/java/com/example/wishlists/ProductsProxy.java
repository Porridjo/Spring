package com.example.wishlists;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "products")
public interface ProductsProxy {

    @GetMapping("/products/{id}")
    Product readOne(@PathVariable int id);

}
