package pl.aleh.UserAccountSecurity.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {

  @NotEmpty(message = "The name should not be empty")
  @Size(min = 6, max = 100, message = "Name must be between 6 and 100 characters")
  private String username;

  @Min(value = 1923, message = "Year of birth must be greater than 1923")
  private int yearOfBirth;

  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(final int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
