package pl.aleh.UserAccountSecurity.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthenticationDTO {

  @NotEmpty(message = "The name should not be empty")
  @Size(min = 6, max = 100, message = "Name must be between 6 and 100 characters")
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
