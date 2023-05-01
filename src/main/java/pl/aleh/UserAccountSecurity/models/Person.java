package pl.aleh.UserAccountSecurity.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Person")
public class Person {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotEmpty(message = "The name should not be empty")
  @Size(min = 6, max = 100, message = "Name must be between 6 and 100 characters")
  @Column(name = "username")
  private String username;

  @Min(value = 1923, message = "Year of birth must be greater than 1923")
  @Column(name = "year_of_birth")
  private int yearOfBirth;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  public Person() {
  }

  public Person(String username, int yearOfBirth) {
    this.username = username;
    this.yearOfBirth = yearOfBirth;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(final String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", yearOfBirth=" + yearOfBirth +
        ", password='" + password + '\'' +
        '}';
  }
}
