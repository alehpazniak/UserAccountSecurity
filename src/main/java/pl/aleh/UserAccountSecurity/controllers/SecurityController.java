package pl.aleh.UserAccountSecurity.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.aleh.UserAccountSecurity.security.PersonDetails;

@RestController
@RequestMapping("/home")
public class SecurityController {

  @GetMapping("/showUserInfo")
  @ResponseBody
  public String showUserInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    return personDetails.getUsername();
  }

}
