package com.example.wishlists;

import org.springframework.stereotype.Service;

@Service
public class WishlistService {

  private final WishlistsRepository repository;

  public WishlistService(WishlistsRepository repository) {
    this.repository = repository;
  }


  public boolean updateOne(Wishlist newWishlist) {
    if (repository.existsByPseudoAndProductId(newWishlist.getPseudo(), newWishlist.getProductId())) return false;

    repository.save(newWishlist);
    return true;
  }


  public boolean deleteOne(String pseudo, int productId) {
    boolean exists = repository.existsByPseudoAndProductId(pseudo, productId);

    if (!exists) return false;
    repository.deleteByPseudoAndId(pseudo, productId);
    return true;
  }

  public Iterable<Wishlist> readFromUser(String pseudo) {
    return repository.findByPseudo(pseudo);
  }

  public void deleteFromUser(String pseudo) {
    repository.deleteByPseudo(pseudo);
  }

  public void deleteFromProductId(int productId) {
    repository.deleteByProductId(productId);
  }
}
