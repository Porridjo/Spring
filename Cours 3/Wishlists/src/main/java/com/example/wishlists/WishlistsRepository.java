package com.example.wishlists;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishlistsRepository extends CrudRepository<Wishlist, Integer> {

  boolean existsByPseudoAndProductId(String pseudo, int productId);

  @Transactional
  void deleteByPseudoAndId(String pseudo, int id);

  Iterable<Wishlist> findByPseudo(String pseudo);

  @Transactional
  void deleteByPseudo(String pseudo);

  @Transactional
  void deleteByProductId(int productId);
}
