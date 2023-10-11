package com.example.wishlists;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "wishlists")
public class Wishlist {
  @Id
  @Column(nullable = false)
  private int id;
  @Column(nullable = false)
  private String pseudo;
  @Column(name = "product_id", nullable = false)
  private int productId;

  public boolean invalid() {
    return id < 0
        || pseudo == null || pseudo.isBlank() || productId < 0;
  }

}
