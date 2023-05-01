package pl.aleh.UserAccountSecurity.controllers;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.aleh.UserAccountSecurity.models.Person;
import pl.aleh.UserAccountSecurity.services.RegistrationService;
import pl.aleh.UserAccountSecurity.validators.PersonValidator;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

  private final PersonValidator personValidator;
  private final RegistrationService registrationService;

  @GetMapping("/login")
  public String getLoginPage() {
    return "auth/login";
  }

  @GetMapping("/registration")
  public String getRegistrationPage(@ModelAttribute("person") Person person) {
    return "/auth/registration";
  }

  @PostMapping("/registration")
  public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
    personValidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "/auth/registration";
    }
    registrationService.register(person);
    return "redirect:/auth/login";
  }

}
