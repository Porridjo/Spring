package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.ProductsProxy;
import be.vinci.ipl.gateway.data.UsersProxy;
import be.vinci.ipl.gateway.data.WishlistsProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.models.Product;
import be.vinci.ipl.gateway.models.UserWithCredentials;
import be.vinci.ipl.gateway.models.Wishlist;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

  private final UsersProxy usersProxy;
  private final WishlistsProxy wishlistsProxy;
  private final AuthenticationProxy authenticationProxy;
  private final ProductsProxy productsProxy;

  public GatewayService(UsersProxy usersProxy, WishlistsProxy wishlistsProxy, AuthenticationProxy authenticationProxy,
                        ProductsProxy productsProxy) {
    this.usersProxy = usersProxy;
    this.wishlistsProxy = wishlistsProxy;
    this.authenticationProxy = authenticationProxy;
    this.productsProxy = productsProxy;
  }

  public void createUser(UserWithCredentials user) throws BadRequestException, ConflictException {
    try {
      usersProxy.createUser(user.getPseudo(), user.toUser());
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 409) throw new ConflictException();
      else throw e;
    }
  }

  public boolean deleteUser(String pseudo) {
    wishlistsProxy.deleteWishlistsFromUser(pseudo);

    boolean found = true;
    try {
      authenticationProxy.deleteCredentials(pseudo);
    } catch (FeignException e) {

      if (e.status() == 404) found = false;
      else throw e;
    }
    try {
      usersProxy.deleteUser(pseudo);
    } catch (FeignException e) {
      if (e.status() == 404) found = false;
      else throw e;
    }

    return found;
  }

  public String verify(String token) {
    try {
      return authenticationProxy.verifyCredentials(token);
    } catch (FeignException e) {
      if (e.status() == 401) return null;
      else throw e;
    }
  }

  public void createWishlist(Wishlist wishlist) throws BadRequestException, NotFoundException {
    try {
      productsProxy.readProduct(wishlist.getProductId());
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }

    try {
      usersProxy.readUser(wishlist.getPseudo());
    } catch (FeignException e) {
      if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }

    try {
      wishlistsProxy.createWishlist(wishlist.getPseudo(), wishlist.getProductId());
    } catch (FeignException e) {
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 404) throw new NotFoundException();
      else throw e;
    }
  }
}
