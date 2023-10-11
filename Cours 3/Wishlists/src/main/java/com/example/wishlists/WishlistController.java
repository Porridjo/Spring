package com.example.wishlists;

import java.util.Iterator;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {

  private final WishlistService service;

  public WishlistController(WishlistService service) {
    this.service = service;
  }


  @PutMapping("/wishlists/{pseudo}/{productId}")
  public ResponseEntity<Void> updateOne(@PathVariable String pseudo, @PathVariable int productId,
      @RequestBody Wishlist wishlist) {
    if (!pseudo.equals(wishlist.getPseudo()) || productId != wishlist.getProductId()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (wishlist.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    boolean updated = service.updateOne(wishlist);

    if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/wishlists/{pseudo}/{productId}")
  public ResponseEntity<Void> deleteOne(@PathVariable String pseudo, @PathVariable int productId) {
    boolean deleted = service.deleteOne(pseudo, productId);

    if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/wishlists/user/{pseudo}")
  public Iterable<Wishlist> readFromUser(@PathVariable String pseudo) {
    return service.readFromUser(pseudo);
  }

  @DeleteMapping("/wishlists/user/{pseudo}")
  public void deleteFromUser(@PathVariable String pseudo) {
    service.deleteFromUser(pseudo);
  }

  @DeleteMapping("/wishlists/product/{productId}")
  public void deleteFromProductId(@PathVariable int productId) {
    service.deleteFromProductId(productId);
  }


}
