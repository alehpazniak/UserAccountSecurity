package pl.aleh.UserAccountSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.aleh.UserAccountSecurity.security.PersonDetails;
import pl.aleh.UserAccountSecurity.services.AdminService;

@Controller
public class SecurityController {

  private final AdminService adminService;

  @Autowired
  public SecurityController(final AdminService adminService) {
    this.adminService = adminService;
  }


  @GetMapping("/hello")
  public String index() {
    return "hello";
  }

  @GetMapping("/showUserInfo")
  public String showUserInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    System.out.println(personDetails.getPerson());
    return "hello";
  }

  @GetMapping("/admin")
  public String adminPage() {
    adminService.doAdminStuff();
    return "admin";
  }

}
