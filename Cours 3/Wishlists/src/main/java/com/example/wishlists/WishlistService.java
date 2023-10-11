package com.example.wishlists;

import org.springframework.stereotype.Service;

@Service
public class WishlistService {

  private final WishlistsRepository repository;
  private final ProductsProxy productsProxy;

  public WishlistService(WishlistsRepository repository, ProductsProxy productsProxy) {
    this.repository = repository;
    this.productsProxy = productsProxy;
  }


  public boolean updateOne(Wishlist newWishlist) {
    Product product = productsProxy.readOne(newWishlist.getProductId());
    if (product == null) return false;

    repository.save(newWishlist);
    return true;
  }


  public boolean deleteOne(String pseudo, int productId) {
    boolean exists = repository.existsByPseudoAndProductId(pseudo, productId);

    if (!exists) return false;
    repository.deleteByPseudoAndProductId(pseudo, productId);
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
