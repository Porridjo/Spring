package be.vinci.ipl.gateway.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
@FeignClient(name = "wishlists")
public interface WishlistsProxy {
    @DeleteMapping("/wishlists/users/{pseudo}")
    void deleteWishlistsFromUser(@PathVariable String pseudo);

    @PostMapping("/wishlists/{pseudo}/{productId}")
    void createWishlist(@PathVariable String pseudo, @PathVariable int productId);
}
