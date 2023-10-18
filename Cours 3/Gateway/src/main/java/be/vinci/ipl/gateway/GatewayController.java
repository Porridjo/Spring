package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.models.UserWithCredentials;
import java.util.Objects;

import be.vinci.ipl.gateway.models.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

  private GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }

  @PostMapping("/users/{pseudo}")
  public ResponseEntity<Void> createUser(@PathVariable String pseudo, @RequestBody
      UserWithCredentials user) {
    if (!Objects.equals(pseudo, user.getPseudo()))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    try {
      service.createUser(user);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ConflictException e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping("/users/{pseudo}")
  public ResponseEntity<Void> deleteUser(@PathVariable String pseudo, @RequestHeader("Authorization") String token) {
    String user = service.verify(token);

    if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(pseudo, user)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    boolean found = service.deleteUser(pseudo);
    if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    else return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/wishlists/{pseudo}/{productId}")
  public ResponseEntity<Void> createWishlist(@PathVariable String pseudo,
                                             @PathVariable int productId,
                                             @RequestBody Wishlist wishlist,
                                             @RequestHeader("Authorization") String token) {
    String user = service.verify(token);

    if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    else if (!Objects.equals(pseudo, user)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    if (!Objects.equals(pseudo, wishlist.getPseudo()) || !Objects.equals(productId, wishlist.getProductId()))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    try {
      service.createWishlist(wishlist);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }



}
