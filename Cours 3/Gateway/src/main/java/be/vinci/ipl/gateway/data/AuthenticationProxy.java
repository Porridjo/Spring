package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {
  @PostMapping("/authentication/{pseudo}")
  void createCredentials(@PathVariable String pseudo, @RequestBody Credentials credentials);

  @DeleteMapping("/authentication/{pseudo}")
  void deleteCredentials(@PathVariable String pseudo);

  @PostMapping("authentication/verify")
  String verifyCredentials(@RequestBody String token);
}
