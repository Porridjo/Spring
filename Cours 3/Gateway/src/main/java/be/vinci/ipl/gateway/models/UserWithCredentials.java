package be.vinci.ipl.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWithCredentials {
  private String pseudo;
  private String password;
  private String firstname;
  private String lastname;

  public User toUser() {
    return new User(pseudo, firstname, lastname);
  }

}
