package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {
  @PostMapping("users/{pseudo}")
  void createUser(@PathVariable String pseudo, @RequestBody User user);

}