package be.vinci.ipl.amazingproducts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
public class ProductsService {
    private ProductsRepository repository;

    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    public Iterable<Product> readAll() {
        return repository.findAll();
    }
    public boolean createOne(Product product) {
        if (repository.existsById(product.getId())) return false;
        repository.save(product);
        return true;
    }

    public boolean deleteOne(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
