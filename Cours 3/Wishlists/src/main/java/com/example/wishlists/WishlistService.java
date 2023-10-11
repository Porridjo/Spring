package com.example.wishlists;

import org.springframework.stereotype.Service;

@Service
public class WishlistService {

  private final WishlistsRepository repository;
  private final ProductsProxy productsProxy;

  private final UsersProxy usersProxy;

  public WishlistService(WishlistsRepository repository, ProductsProxy productsProxy, UsersProxy usersProxy) {
    this.repository = repository;
    this.productsProxy = productsProxy;
    this.usersProxy = usersProxy;
  }


  public boolean updateOne(Wishlist newWishlist) {
    Product product = productsProxy.readOne(newWishlist.getProductId());
    if (product == null) return false;

    User user = usersProxy.readOne(newWishlist.getPseudo());
    if (user == null) return false;

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
