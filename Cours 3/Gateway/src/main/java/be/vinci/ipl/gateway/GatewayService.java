package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.UsersProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.models.UserWithCredentials;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

  private final UsersProxy usersProxy;

  public GatewayService(UsersProxy usersProxy) {
    this.usersProxy = usersProxy;
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
}
