package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.models.UserWithCredentials;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ConflictException e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }


}
