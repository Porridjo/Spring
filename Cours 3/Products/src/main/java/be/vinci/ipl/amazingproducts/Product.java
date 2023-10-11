package be.vinci.ipl.amazingproducts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "products")

public class Product {
    @Id
    private int id;
    private String name;
    private String category;
    private double price;
}
