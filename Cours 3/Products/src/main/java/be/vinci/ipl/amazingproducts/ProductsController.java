package be.vinci.ipl.amazingproducts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ProductsController {
    private ProductsService service;

    public ProductsController(ProductsService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public Iterable<Product> readAll() {
        return service.readAll();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {
        boolean created = service.createOne(product);
        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteOne(@PathVariable int id) {
        boolean deleted = service.deleteOne(id);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

}


